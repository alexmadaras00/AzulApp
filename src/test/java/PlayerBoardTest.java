import dataobjects.PlayerBoardState;
import dataobjects.ScoreChange;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ExceptionInvalidOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBoardTest {
    private static PlayerBoard playerBoard;
    private static Wall wall;
    static FloorLine floorLine;
    static PatternLine patternLine;
    static int score;
    static List<ScoreChange> scoreChanges;

    @BeforeEach
    public void init() {
        wall = new Wall();
        patternLine = new PatternLine();
        floorLine = new FloorLine();
        scoreChanges = new ArrayList<>();
        playerBoard = new PlayerBoard(wall, patternLine, floorLine, score, scoreChanges);
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(patternLine);
        assertEquals(5, patternLine.getCopyTable().size());
        assertNotNull(wall);
        assertEquals(5, wall.getCopyTable().size());
        assertNotNull(floorLine);
        assertEquals(0, score);
        assertNotNull(scoreChanges);
    }

    @Test
    public void testCanAddTypePatternLine() {
        int row = 0;
        Color c = Color.RED;
        boolean valueWall = wall.canAddTile(row, c);
        boolean valuePatternLine = patternLine.canAddTile(row, c);
        assertEquals(valueWall && valuePatternLine, playerBoard.canAddTypePatternLine(row, c));
        assertTrue(valuePatternLine);
        assertTrue(valueWall);
        assertTrue(playerBoard.canAddTypePatternLine(row, c));
        List<Tile> tiles = List.of(c);
        playerBoard.performMovePatternLine(row, tiles);
        assertFalse(patternLine.canAddTile(row, c));
        assertFalse(playerBoard.canAddTypePatternLine(row, c));
    }

    @Test
    public void testPerformMovePatternLine() {
        int rowIndex = 0;
        Color blueTile = Color.BLUE;
        List<Tile> tiles = List.of(blueTile);
        assertTrue(wall.canAddTile(rowIndex, blueTile));
        assertTrue(patternLine.canAddTile(rowIndex, blueTile));
        assertTrue(playerBoard.canAddTypePatternLine(rowIndex, blueTile));
        playerBoard.performMovePatternLine(rowIndex, tiles);
        assertEquals(1, patternLine.getCopyTable().get(rowIndex).size());
        assertEquals(tiles, patternLine.getCopyTable().get(0));
        assertFalse(patternLine.canAddTile(rowIndex, blueTile));
        assertFalse(playerBoard.canAddTypePatternLine(rowIndex, blueTile));
        try {
            playerBoard.performMovePatternLine(0, tiles);
            fail("Expected exception not thrown");
        } catch (ExceptionInvalidOperation e) {
            // Assert that the exception message is correct
            assertEquals("Pattern Line Invalid move. Cannot add a tile in the current format.", e.getMessage());
        }
    }

    @Test
    public void testPerformMoveFloorLine() {
        Color redTile = Color.RED;
        Color yellowTile = Color.YELLOW;
        List<Tile> tiles = Arrays.asList(redTile, redTile, redTile, redTile, redTile, redTile, redTile);
        List<Tile> moreTiles = Arrays.asList(yellowTile);
        assertTrue(floorLine.getCopyTiles().size() <= 7);
        playerBoard.performMoveFloorLine(tiles);
        assertEquals(7, floorLine.getCopyTiles().size());
        assertEquals(tiles, floorLine.getCopyTiles());
        playerBoard.performMoveFloorLine(moreTiles);
        assertEquals(tiles, floorLine.getCopyTiles());
    }

    @Test
    public void testWallTilting() {
        int completedRow = 1;
        Tile clearedTile = Color.RED;
        Tile floorLineTile = Color.BLUE;
        List<Tile> addedTiles = List.of(clearedTile, clearedTile);
        List<Tile> clearedTiles = new ArrayList<>(List.of(clearedTile));
        List<Tile> floorLineTiles = List.of(floorLineTile, floorLineTile);
        ScoreChange scoreChange = new ScoreChange();
        List<Integer> completedRows = List.of(completedRow);
        floorLine.addTiles(floorLineTiles);
        patternLine.addTiles(completedRow, addedTiles);
        assertEquals(completedRows, patternLine.completedRows());
        List<Tile> remainingTiles = new ArrayList<>(clearedTiles);
        remainingTiles.addAll(floorLineTiles);
        //Checking if the wallTilting method returns a remaining list of floorLine tiles
        // and exceeded tiles (size=length(completedRow)-1) from patternLine
        assertTrue(wall.canAddTile(completedRow, clearedTile));
        assertEquals(remainingTiles, playerBoard.wallTilting());
        List<Tile> checkClearedTiles = patternLine.getCopyTable().get(completedRow).subList(1, patternLine.clearTiles(completedRow).size());
        assertEquals(clearedTiles, checkClearedTiles);
        //Testing the adding operation of the tile to the wall

        assertTrue(wall.getCopyTable().get(completedRow).contains(clearedTile));
        assertFalse(wall.canAddTile(completedRow, clearedTile));
        //Checking if the new ScoreChange Object has been created
        assertNotNull(scoreChange);
        assertEquals(0, floorLine.getCopyTiles().size());
        //Checking again if the new ScoreChange Object has been created, after deleting the floorLine
        assertNotNull(scoreChange);
    }

    @Test
    public void testAddFinalScores() {
        //Init
        int scoreDifference = 0, initialScore = score;
        wall.addTile(0, Color.RED);
        wall.addTile(0, Color.YELLOW);
        wall.addTile(0, Color.CYAN);
        wall.addTile(0, Color.BLUE);
        wall.addTile(0, Color.BLACK);
        //Calling the tested method
        playerBoard.addFinalScores();

        List<ScoreChange> scoreChangeList = wall.getCompletionScores();
        //Checking if the the method updates the List of ScoreChange objects when an event occurs
        //event = row full, color completion, column full
        assertTrue(wall.hasCompleteRow());
        assertTrue(scoreChangeList instanceof LinkedList<ScoreChange>);
        assertEquals(1, scoreChangeList.size());
        //Checking if the score is indeed updated
        ////score = row: 2, color completion, column: 7, color :10
        for (ScoreChange sc : scoreChangeList)
            scoreDifference += sc.getScoreDifference();
        assertEquals(2, initialScore + scoreDifference);
    }

    @Test
    public void testToObject() {
        PlayerBoardState playerBoardState = new PlayerBoardState();
        playerBoardState.setWall(wall.getCopyTable());
        playerBoardState.setFloorLine(floorLine.getCopyTiles());
        playerBoardState.setPatternLine(patternLine.getCopyTable());
        //playerBoardState.setScoreChanges(scoreChanges);
        playerBoardState.setScore(score);
        assertNotNull(playerBoardState);
        assertEquals(playerBoardState, playerBoard.toObject());
    }
}
