package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataobjects.ScoreChange;
import model.Color;
import model.Wall;

public class WallTest {
    private static Tile[][] template;
    private static Tile[] colors = Color.values();

    // 01234
    // 40123
    // 34012
    // 23401
    // 12340
    @BeforeAll
    public static void setUp() {
        int size = colors.length;
        template = new Tile[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                template[row][col] = colors[(col - row + size) % size];
            }
        }
        System.out.println(template);
        return;
    }

    private Wall wall;

    @BeforeEach
    public void setUpEach() {
        wall = new Wall();
        return;
    }

    private void assertEqualWall(Tile[][] wall1, List<List<Tile>> wall2) {
        for (int row = 0; row < wall1.length; row++) {
            for (int col = 0; col < wall1[row].length; col++) {
                assertEquals(wall1[row][col], wall2.get(row).get(col));
            }
        }
    }

    @Test
    public void testGetCopyTableEmpty() {
        Tile[][] testWall = new Tile[colors.length][colors.length];
        assertEqualWall(testWall, wall.getCopyTable());
        return;
    }

    @Test
    public void testGetCopyTablePartial() {
        for (Tile c : colors) {
            wall.addTile(4, c);
        }
        Tile[][] testWall = new Tile[colors.length][colors.length];
        testWall[4] = template[4];
        assertEqualWall(testWall, wall.getCopyTable());
        return;
    }

    @Test
    public void testGetCopyTableFull() {
        for (int row = 0; row < colors.length; row++) {
            for (Tile c : colors) {
                wall.addTile(row, c);
            }
        }
        assertEqualWall(template, wall.getCopyTable());
        return;
    }

    @Test
    public void testCanAddTiles() {
        wall.addTile(0, colors[0]);
        assertEquals(wall.canAddTile(0, colors[0]), false);
        for (int c = 1; c < colors.length; c++) {
            assertEquals(wall.canAddTile(0, colors[c]), true);
            assertEquals(wall.canAddTile(c, colors[0]), true);
        }
        return;
    }

    @Test
    public void testHasCompleteRowFull() {
        assertEquals(false, wall.hasCompleteRow());
        for (Tile c : colors) {
            wall.addTile(2, c);
        }
        assertEquals(true, wall.hasCompleteRow());
        return;
    }

    @Test
    public void testHasCompleteRowPartial() {
        assertEquals(false, wall.hasCompleteRow());
        for (int r = 0; r < colors.length; r++) {
            wall.addTile(r, colors[0]);
        }
        assertEquals(false, wall.hasCompleteRow());
        return;
    }

    @Test
    public void testHasCompleteRowSome() {
        assertEquals(false, wall.hasCompleteRow());
        for (Tile c : colors) {
            wall.addTile(0, c);
        }
        for (int r = 1; r < colors.length; r++) {
            wall.addTile(r, colors[0]);
        }
        assertEquals(true, wall.hasCompleteRow());
        return;
    }

    // SCORE TESTING

    @Test
    public void testGetCompletionScoreColumns() {
        assertEquals(true, wall.getCompletionScores().isEmpty());
        for (int col = 1; col < colors.length; col++) {
            for (int row = 0; row < colors.length; row++) {
                wall.addTile(row, wall.getTemplateColor(row, col));
            }
            List<ScoreChange> scoreChange = wall.getCompletionScores();
            assertEquals(col, scoreChange.size());
            for (ScoreChange sc : scoreChange) {
                //assertEquals(true, sc.getIsCompletionScore());
                //assertEquals(false, sc.getHasColor());
                //assertEquals(false, sc.getHasRowIndex());
                assertEquals(null, sc.getColor());
                assertInstanceOf(Integer.class, sc.getIndex());
                assertEquals(true, sc.getIndex() < colors.length);
                assertEquals(true, sc.getIndex() >= 0);
                assertEquals(7, sc.getScoreDifference());
            }
        }
        return;
    }

    @Test
    public void testGetCompletionScoresRows() {
        assertEquals(true, wall.getCompletionScores().isEmpty());
        for (int r = 1; r < colors.length; r++) {
            for (Tile c : colors) {
                wall.addTile(r, c);
            }
            List<ScoreChange> scoreChange = wall.getCompletionScores();
            assertEquals(r, scoreChange.size());
            for (ScoreChange sc : scoreChange) {
                //assertEquals(true, sc.getIsCompletionScore());
                //assertEquals(false, sc.getHasColor());
                //assertEquals(true, sc.getHasRowIndex());
                assertEquals(null, sc.getColor());
                assertInstanceOf(Integer.class, sc.getIndex());
                assertEquals(true, sc.getIndex() < colors.length);
                assertEquals(true, sc.getIndex() >= 0);
                assertEquals(2, sc.getScoreDifference());
            }
        }
        return;
    }

    @Test
    public void testGetCompletionScoresColor() {
        assertEquals(true, wall.getCompletionScores().isEmpty());
        for (int c = 1; c < colors.length; c++) {
            for (int r = 0; r < colors.length; r++) {
                wall.addTile(r, colors[c]);
            }
            List<ScoreChange> scoreChange = wall.getCompletionScores();
            assertEquals(c, scoreChange.size());
            for (ScoreChange sc : scoreChange) {
                //assertEquals(true, sc.getIsCompletionScore());
                //assertEquals(true, sc.getHasColor());
                //assertEquals(false, sc.getHasRowIndex());
                assertEquals(0, sc.getIndex());
                assertInstanceOf(Color.class, sc.getColor());
                assertEquals(true, Arrays.asList(colors).contains(sc.getColor()));
                assertEquals(10, sc.getScoreDifference());
            }
        }
        return;
    }

    @Test
    public void testScoreEmpty() {
        int score = wall.addTile(2, colors[0]);
        assertEquals(1, score);
        return;
    }

    @Test
    public void testScoreHorizontal() {
        assertEquals(5, colors.length);
        int[] tilesPlaced = { 4, 1, 2 };
        for (int tile : tilesPlaced) {
            wall.addTile(2, colors[tile]);
        }
        int score = wall.addTile(2, colors[0]);
        assertEquals(4, score);
        return;
    }

    @Test
    public void testScoreVertical() {
        assertEquals(5, colors.length);
        int[] rowsPlaced = { 1, 3, 4 };
        for (int row : rowsPlaced) {
            wall.addTile(row, wall.getTemplateColor(row, 2));
        }
        int score = wall.addTile(2, colors[0]);
        assertEquals(4, score);
        return;
    }

    @Test
    public void testScoreHorizontalAndVertical() {
        assertEquals(5, colors.length);
        int[] rowsPlaced = { 1, 3, 4 };
        for (int row : rowsPlaced) {
            wall.addTile(row, wall.getTemplateColor(row, 2));
        }
        int[] tilesPlaced = { 4, 1, 2 };
        for (int tile : tilesPlaced) {
            wall.addTile(2, colors[tile]);
        }
        int score = wall.addTile(2, colors[0]);
        assertEquals(8, score);
        return;
    }

    // Unmodifiable list check

    @Test
    public void testUnmodifyableCopyTable() {
        wall.addTile(0, colors[0]);
        List<List<Tile>> copyTable = wall.getCopyTable();
        List<Tile> newRow = new ArrayList<>(Arrays.asList(colors));
        assertThrows(UnsupportedOperationException.class, () -> copyTable.set(1, newRow));
        assertThrows(UnsupportedOperationException.class, () -> copyTable.get(1).set(1, colors[0]));
        assertThrows(UnsupportedOperationException.class, () -> copyTable.clear());
        assertThrows(UnsupportedOperationException.class, () -> copyTable.get(1).add(colors[0]));
        assertThrows(UnsupportedOperationException.class, () -> copyTable.add(newRow));
    }
}
