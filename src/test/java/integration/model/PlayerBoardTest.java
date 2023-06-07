package integration.model;

import dataobjects.data.PlayerBoardState;
import dataobjects.data.ScoreChange;
import model.PlayerBoard;
import model.Tile;
import model.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBoardTest {
    private static PlayerBoard playerBoard;

    @BeforeEach
    public void init() {
        playerBoard = new PlayerBoard();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(playerBoard.getPatternLine());
        assertEquals(5, playerBoard.getPatternLine().getCopyTable().size());
        assertNotNull(playerBoard.getWall());
        assertEquals(5, playerBoard.getWall().getCopyTable().size());
        assertNotNull(playerBoard.getFloorLine());
        assertEquals(0, playerBoard.getScore());
        assertNotNull(playerBoard.getScoreChanges());
    }

    @Test
    public void testCanAddTypePatternLine() {
        int row = 0;
        TileColor c = TileColor.RED;
        boolean valueWall = playerBoard.getWall().canAddTile(row, c);
        boolean valuePatternLine = playerBoard.getPatternLine().canAddTile(row, c);
        assertEquals(valueWall && valuePatternLine, playerBoard.canAddTypePatternLine(row, c));
        assertTrue(valuePatternLine);
        assertTrue(valueWall);
        assertTrue(playerBoard.canAddTypePatternLine(row, c));
        List<Tile> tiles = List.of(c);
        playerBoard.performMovePatternLine(row, tiles);
        assertFalse(playerBoard.getPatternLine().canAddTile(row, c));
        assertFalse(playerBoard.canAddTypePatternLine(row, c));
    }

    @Test
    public void testPerformMovePatternLine() {
        TileColor blueTile = TileColor.BLUE;
        TileColor redTile = TileColor.RED;
        assertExceedingTilesCase(redTile);
        assertNoExceedingTilesCase(blueTile);
        assertRuntimeExceptionCase();
    }

    private void assertRuntimeExceptionCase() {
        playerBoard.getPatternLine().clearTiles(1);
        playerBoard.getPatternLine().addTiles(1, List.of(TileColor.BLUE));
        try {
            playerBoard.performMovePatternLine(1, List.of(TileColor.YELLOW));
        } catch (RuntimeException e) {
            assertEquals("This tile cannot be added on the current pattern line.", e.getMessage());
        }
        playerBoard.getPatternLine().addTiles(1, List.of(TileColor.BLUE));
        try {
            playerBoard.performMovePatternLine(1, List.of(TileColor.BLUE));
            fail("Expected exception not thrown");
        } catch (RuntimeException e) {
            assertEquals("This tile cannot be added on the current pattern line.", e.getMessage());
        }
    }

    private void assertExceedingTilesCase(TileColor tile) {
        List<Tile> extraTiles = List.of(tile, tile, tile, tile, tile, tile);
        List<Tile> patternLineExcessTiles = playerBoard.performMovePatternLine(0, extraTiles);
        assertEquals(5, patternLineExcessTiles.size());


        List<Tile> patternLineExcessTiles2 = playerBoard.performMovePatternLine(1, extraTiles);
        assertEquals(4, patternLineExcessTiles2.size());


        List<Tile> patternLineExcessTiles3 = playerBoard.performMovePatternLine(2, extraTiles);
        assertEquals(3, patternLineExcessTiles3.size());

        List<Tile> patternLineExcessTiles4 = playerBoard.performMovePatternLine(3, extraTiles);
        assertEquals(2, patternLineExcessTiles4.size());

        List<Tile> patternLineExcessTiles5 = playerBoard.performMovePatternLine(4, extraTiles);
        assertEquals(1, patternLineExcessTiles5.size());

    }

    private void assertNoExceedingTilesCase(TileColor tile) {
        playerBoard.getPatternLine().clearTiles(0);

        List<Tile> tiles = List.of(tile);
        assertTrue(playerBoard.getWall().canAddTile(0, tile));
        assertTrue(playerBoard.getPatternLine().canAddTile(0, tile));
        assertTrue(playerBoard.canAddTypePatternLine(0, tile));
        List<Tile> patternLineExcessTiles = playerBoard.performMovePatternLine(0, tiles);
        assertEquals(1, playerBoard.getPatternLine().getCopyTable().get(0).size());
        assertEquals(0, patternLineExcessTiles.size());
        assertEquals(tiles, playerBoard.getPatternLine().getCopyTable().get(0));
        assertFalse(playerBoard.getPatternLine().canAddTile(0, tile));
        assertFalse(playerBoard.canAddTypePatternLine(0, tile));
    }

    @Test
    public void testPerformMoveFloorLine() {
        TileColor redTile = TileColor.RED;
        List<Tile> tiles = Arrays.asList(redTile, redTile, redTile, redTile, redTile, redTile, redTile);
        assertTrue(playerBoard.getFloorLine().getCopyTiles().size() <= 7);
        playerBoard.performMoveFloorLine(tiles);
        assertEquals(7, playerBoard.getFloorLine().getCopyTiles().size());
        assertEquals(tiles, playerBoard.getFloorLine().getCopyTiles());
        assertEquals(playerBoard.getFloorLine().addTiles(tiles), playerBoard.performMoveFloorLine(tiles));
        assertEquals(tiles, playerBoard.getFloorLine().getCopyTiles());
    }

    @Test
    public void testWallTilting() {
        int completedRow = 1;
        Tile clearedTile = TileColor.RED;
        Tile floorLineTile = TileColor.BLUE;
        List<Tile> addedTiles = List.of(clearedTile, clearedTile);
        List<Tile> clearedTiles = new ArrayList<>(List.of(clearedTile));
        List<Tile> floorLineTiles = List.of(floorLineTile, floorLineTile);
        ScoreChange scoreChange = new ScoreChange();
        List<Integer> completedRows = List.of(completedRow);
        playerBoard.getFloorLine().addTiles(floorLineTiles);
        playerBoard.getPatternLine().addTiles(completedRow, addedTiles);
        assertEquals(completedRows, playerBoard.getPatternLine().completedRows());
        List<Tile> remainingTiles = new ArrayList<>(clearedTiles);
        remainingTiles.addAll(floorLineTiles);
        //Checking if the wallTilling method returns a remaining list of floorLine tiles
        // and exceeded tiles (size=length(completedRow)-1) from patternLine
        assertTrue(playerBoard.getWall().canAddTile(completedRow, clearedTile));
        assertEquals(remainingTiles, playerBoard.wallTilling());
        //Testing the adding operation of the tile to the wall

        assertTrue(playerBoard.getWall().getCopyTable().get(completedRow).contains(clearedTile));
        assertFalse(playerBoard.getWall().canAddTile(completedRow, clearedTile));
        //Checking if the new ScoreChange Object has been created
        assertNotNull(scoreChange);
        assertEquals(0, playerBoard.getFloorLine().getCopyTiles().size());
        assertEquals(0, playerBoard.getPatternLine().getCopyTable().get(completedRow).size());
        //Checking again if the new ScoreChange Object has been created, after deleting the floorLine
        assertNotNull(scoreChange);
    }

    @Test
    public void testHasFufliffledFinalCondition() {
        assertEquals(playerBoard.hasFulfilledEndCondition(), playerBoard.getWall().hasCompleteRow());
    }

    @Test
    public void testAddFinalScores() {
        //Init
        int scoreDifference = 0, initialScore = playerBoard.getScore();
        playerBoard.getWall().addTile(0, TileColor.RED);
        playerBoard.getWall().addTile(0, TileColor.YELLOW);
        playerBoard.getWall().addTile(0, TileColor.CYAN);
        playerBoard.getWall().addTile(0, TileColor.BLUE);
        playerBoard.getWall().addTile(0, TileColor.BLACK);
        //Calling the tested method
        playerBoard.addFinalScores();

        List<ScoreChange> scoreChangeList = playerBoard.getWall().getCompletionScores();
        //Checking if the the method updates the List of ScoreChange objects when an event occurs
        //event = row full, color completion, column full
        assertTrue(playerBoard.getWall().hasCompleteRow());
        assertTrue(scoreChangeList instanceof LinkedList<ScoreChange>);
        assertEquals(1, scoreChangeList.size());
        //Checking if the score is indeed updated
        ////score = row: 2, color completion, column: 7, color :10
        AtomicInteger wallScore = new AtomicInteger();
        int floorLineScore = playerBoard.getFloorLine().getScore();
        scoreChangeList.forEach(scoreChange -> wallScore.addAndGet(scoreChange.getScoreDifference()));
        int finalScore = playerBoard.getScore();
        assertEquals(wallScore.get() - floorLineScore, finalScore);
        assertNotEquals(initialScore, finalScore);
    }

    @Test
    public void testToObject() {
        PlayerBoardState playerBoardState = new PlayerBoardState();
        playerBoardState.setWall(playerBoard.getWall().getCopyTable());
        playerBoardState.setFloorLine(playerBoard.getFloorLine().getCopyTiles());
        playerBoardState.setPatternLine(playerBoard.getPatternLine().getCopyTable());
        playerBoardState.setScore(playerBoard.getScore());
        assertNotNull(playerBoardState);
        assertEquals(playerBoardState, playerBoard.toObject());
    }
}
