package unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Wall;
import shared.Tile;
import shared.TileColor;

public class WallTest {
    private static Tile[][] template;
    private static Tile[] colors = TileColor.values();

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
    public void testTemplateWall() {
        for (int row = 0; row < template.length; row++) {
            for (int col = 0; col < template[row].length; col++) {
                assertEquals(template[row][col], Wall.wallPattern().get(row).get(col));
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
    public void testGetCompletedRowCountFull() {
        assertEquals(0, wall.getCompletedRowCount());
        for (Tile c : colors) {
            wall.addTile(2, c);
        }
        assertEquals(1, wall.getCompletedRowCount());
        return;
    }

    @Test
    public void testGetCompletedRowCountPartial() {
        assertEquals(0, wall.getCompletedRowCount());
        for (int r = 0; r < colors.length; r++) {
            wall.addTile(r, colors[0]);
        }
        assertEquals(0, wall.getCompletedRowCount());
        return;
    }

    @Test
    public void testGetCompletedRowCountCount() {
        assertEquals(0, wall.getCompletedRowCount());
        for (Tile c : colors) {
            wall.addTile(0, c);
        }
        for (int r = 1; r < colors.length; r++) {
            wall.addTile(r, colors[0]);
        }
        assertEquals(1, wall.getCompletedRowCount());
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
            List<Integer> scoreChange = wall.getCompletionScores();
            assertEquals(col, scoreChange.size());
            for (Integer sc : scoreChange) {
                assertEquals(7, sc);
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
            List<Integer> scoreChange = wall.getCompletionScores();
            assertEquals(r, scoreChange.size());
            for (Integer sc : scoreChange) {
                assertEquals(2, sc);
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
            List<Integer> scoreChange = wall.getCompletionScores();
            assertEquals(c, scoreChange.size());
            for (Integer sc : scoreChange) {
                assertEquals(10, sc);
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
