package model;

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
        Color c = Color.RED;
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
        int rowIndex = 0;
        Color blueTile = Color.BLUE;
        List<Tile> tiles = List.of(blueTile);
        assertTrue(playerBoard.getWall().canAddTile(rowIndex, blueTile));
        assertTrue(playerBoard.getPatternLine().canAddTile(rowIndex, blueTile));
        assertTrue(playerBoard.canAddTypePatternLine(rowIndex, blueTile));
        playerBoard.performMovePatternLine(rowIndex, tiles);
        assertEquals(1, playerBoard.getPatternLine().getCopyTable().get(rowIndex).size());
        assertEquals(tiles, playerBoard.getPatternLine().getCopyTable().get(0));
        assertFalse(playerBoard.getPatternLine().canAddTile(rowIndex, blueTile));
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
        assertTrue(playerBoard.getFloorLine().getCopyTiles().size() <= 7);
        playerBoard.performMoveFloorLine(tiles);
        assertEquals(7, playerBoard.getFloorLine().getCopyTiles().size());
        assertEquals(tiles, playerBoard.getFloorLine().getCopyTiles());
        playerBoard.performMoveFloorLine(moreTiles);
        assertEquals(tiles, playerBoard.getFloorLine().getCopyTiles());
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
        playerBoard.getFloorLine().addTiles(floorLineTiles);
        playerBoard.getPatternLine().addTiles(completedRow, addedTiles);
        assertEquals(completedRows, playerBoard.getPatternLine().completedRows());
        List<Tile> remainingTiles = new ArrayList<>(clearedTiles);
        remainingTiles.addAll(floorLineTiles);
        //Checking if the wallTilting method returns a remaining list of floorLine tiles
        // and exceeded tiles (size=length(completedRow)-1) from patternLine
        assertTrue(playerBoard.getWall().canAddTile(completedRow, clearedTile));
        assertEquals(remainingTiles, playerBoard.wallTilting());
        List<Tile> checkClearedTiles = playerBoard.getPatternLine().getCopyTable().get(completedRow).subList(1, playerBoard.getPatternLine().clearTiles(completedRow).size());
        assertEquals(clearedTiles, checkClearedTiles);
        //Testing the adding operation of the tile to the wall

        assertTrue(playerBoard.getWall().getCopyTable().get(completedRow).contains(clearedTile));
        assertFalse(playerBoard.getWall().canAddTile(completedRow, clearedTile));
        //Checking if the new ScoreChange Object has been created
        assertNotNull(scoreChange);
        assertEquals(0, playerBoard.getFloorLine().getCopyTiles().size());
        //Checking again if the new ScoreChange Object has been created, after deleting the floorLine
        assertNotNull(scoreChange);
    }

    @Test
    public void testAddFinalScores() {
        //Init
        int scoreDifference = 0, initialScore = playerBoard.getScore();
        playerBoard.getWall().addTile(0, Color.RED);
        playerBoard.getWall().addTile(0, Color.YELLOW);
        playerBoard.getWall().addTile(0, Color.CYAN);
        playerBoard.getWall().addTile(0, Color.BLUE);
        playerBoard.getWall().addTile(0, Color.BLACK);
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
        for (ScoreChange sc : scoreChangeList)
            scoreDifference += sc.getScoreDifference();
        assertEquals(2, initialScore + scoreDifference);
    }

    @Test
    public void testToObject() {
        PlayerBoardState playerBoardState = new PlayerBoardState();
        playerBoardState.setWall(playerBoard.getWall().getCopyTable());
        playerBoardState.setFloorLine(playerBoard.getFloorLine().getCopyTiles());
        playerBoardState.setPatternLine(playerBoard.getPatternLine().getCopyTable());
        //playerBoardState.setScoreChanges(playerBoard.getScoreChanges());
        playerBoardState.setScore(playerBoard.getScore());
        assertNotNull(playerBoardState);
        assertEquals(playerBoardState, playerBoard.toObject());
    }
}
