package integration.model;


import messaging.dataobjects.*;
import model.PlayerBoard;
import model.Tile;
import model.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
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
        List<Tile> clearedTiles = new ArrayList<>(List.of(clearedTile, clearedTile));
        List<Tile> floorLineTiles = List.of(floorLineTile, floorLineTile);
        ScoreChange wallTileScoreChange = new ScoreChange();
        ScoreChange floorLineScoreChange = new ScoreChange();
        List<Integer> completedRows = List.of(completedRow);
        List<ScoreChange> scoreChanges = new ArrayList<>();

        playerBoard.getFloorLine().addTiles(floorLineTiles);
        playerBoard.getPatternLine().addTiles(completedRow, addedTiles);
        assertEquals(completedRows, playerBoard.getPatternLine().completedRows());
        HashMap<Location,List<Tile>> remainingTiles = new HashMap<Location, List<Tile>>();
        remainingTiles.put(new Location(LocationType.PATTERN_LINE, 1), clearedTiles);
        remainingTiles.put(new Location(LocationType.FLOOR_LINE, 0), floorLineTiles);
        //Checking if the wallTilling method returns a remaining list of floorLine tiles
        // and exceeded tiles (size=length(completedRow)-1) from patternLine
        assertTrue(playerBoard.getWall().canAddTile(completedRow, clearedTile));
        wallTileScoreChange.setType(ScoreType.PLACED_TILE_IN_WALL);
        wallTileScoreChange.setIndex(completedRow);
        wallTileScoreChange.setColor((TileColor) clearedTile);
        wallTileScoreChange.setScoreDifference(1);
        floorLineScoreChange.setType(ScoreType.CLEARED_FLOOR_LINE);
        floorLineScoreChange.setScoreDifference(-1); // capped out at -1
        scoreChanges.add(wallTileScoreChange);
        scoreChanges.add(floorLineScoreChange);
        
        HashMap<Location,List<Tile>> tilesWallTiling = playerBoard.wallTilling();
        List<Location> wallKeySet = new ArrayList<>(tilesWallTiling.keySet());
        if (wallKeySet.get(0).getType() != LocationType.PATTERN_LINE) {
            wallKeySet.add(wallKeySet.remove(0));
        }
        List<Location> remainingKeySet = new ArrayList<>(remainingTiles.keySet());
        if (remainingKeySet.get(0).getType() != LocationType.PATTERN_LINE) {
            remainingKeySet.add(remainingKeySet.remove(0));
        }
        assertEquals(remainingKeySet.size(),tilesWallTiling.keySet().size());
        for(int i = 0; i< remainingKeySet.size();i++){
            assertEquals(remainingKeySet.get(i).getType(),wallKeySet.get(i).getType());
            assertEquals(remainingTiles.get(remainingKeySet.get(i)), tilesWallTiling.get(wallKeySet.get(i)));
            if (remainingKeySet.get(i).getType().equals(LocationType.PATTERN_LINE))
                assertEquals(remainingKeySet.get(i).getIndex(), 1);
            
        }        

        //Testing the adding operation of the tile to the wall
        assertTrue(playerBoard.getWall().getCopyTable().get(completedRow).contains(clearedTile));
        assertFalse(playerBoard.getWall().canAddTile(completedRow, clearedTile));
        //Checking if the new ScoreChange Object has been created
        
        assertEquals(0, playerBoard.getFloorLine().getCopyTiles().size());
        assertEquals(0, playerBoard.getPatternLine().getCopyTable().get(completedRow).size());
        List<ScoreChange> boardScoreChanges = playerBoard.getScoreChanges();
        for (int i = 0; i < scoreChanges.size(); i++) {
            assertEquals(scoreChanges.get(i).getType(), boardScoreChanges.get(i).getType());
            assertEquals(scoreChanges.get(i).getColor(), boardScoreChanges.get(i).getColor());
            assertEquals(scoreChanges.get(i).getIndex(), boardScoreChanges.get(i).getIndex());
            assertEquals(scoreChanges.get(i).getScoreDifference(), boardScoreChanges.get(i).getScoreDifference());
            // assertTrue(scoreChanges.get(i).equals(boardScoreChanges.get(i)));
        }
       
    }

    @Test
    public void testHasFufliffledFinalCondition() {
        assertEquals(playerBoard.hasFulfilledEndCondition(),playerBoard.getWall().getCompletedRowCount()>0);
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

        int finalScore = playerBoard.getScore();
        assertNotEquals(initialScore, finalScore);
        assertEquals(2, finalScore);
    }

    @Test
    public void testNegativeScore() {
        playerBoard.performMovePatternLine(0, List.of(TileColor.RED)); // add 1
        playerBoard.performMoveFloorLine(List.of(TileColor.RED, TileColor.RED)); // remove 2 (capped at 1)
        playerBoard.wallTilling();
        assertEquals(0, playerBoard.getScore());
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
    @Test
    public void getCompletedRowCount(){
        assertEquals(playerBoard.getCompletedRowCount(), playerBoard.getWall().getCompletedRowCount());
    }
}
