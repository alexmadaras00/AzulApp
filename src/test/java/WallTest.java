import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Color;
import model.Wall;

public class WallTest {
    private static Color[][] template;
    private static Color[] colors = Color.values();

    // 01234
    // 40123
    // 34012
    // 23401
    // 12340
    @BeforeAll
    public static void setUp() {
        int size = colors.length;
        template = new Color[size][size];
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

    private void assertEqualWall(Color[][] wall1, Color[][] wall2) {
        for (int row = 0; row < wall1.length; row++) {
            for (int col = 0; col < wall1[row].length; col++) {
                assertEquals(wall1[row][col], wall2[row][col]);
            }
        }
    }

    @Test
    public void testGetCopyTableEmpty() {
        Color[][] testWall = new Color[colors.length][colors.length];
        assertEqualWall(testWall, wall.getCopyTable());
        return;
    }

    @Test
    public void testGetCopyTablePartial() {
        for (Color c : colors) {
            wall.addTile(4, c);
        }
        Color[][] testWall = new Color[colors.length][colors.length];
        testWall[4] = template[4];
        assertEqualWall(testWall, wall.getCopyTable());
        return;
    }

    @Test
    public void testGetCopyTableFull() {
        for (int row = 0; row < colors.length; row++) {
            for (Color c : colors) {
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
        for (Color c : colors) {
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
        for (Color c : colors) {
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
        assertEquals(0, wall.getCompletionScores());
        for (int col = 1; col < colors.length; col++) {
            for (int row = 0; row < colors.length; row++) {
                wall.addTile(row, wall.getTemplateColor(row, col));
            }
            assertEquals(col * 7, wall.getCompletionScores());
        }
        return;
    }

    @Test
    public void testGetCompletionScoresRows() {
        assertEquals(0, wall.getCompletionScores());
        for (int r = 1; r < colors.length; r++) {
            for (Color c : colors) {
                wall.addTile(r, c);
            }
            assertEquals(r * 2, wall.getCompletionScores());
        }
        return;
    }

    @Test
    public void testGetCompletionScoresColor() {
        assertEquals(0, wall.getCompletionScores());
        for (int c = 1; c < colors.length; c++) {
            for (int r = 0; r < colors.length; r++) {
                wall.addTile(r, colors[c]);
            }
            assertEquals(c * 10, wall.getCompletionScores());
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

    public static void main(String[] args) {
        System.out.println(-11 % 5);

    }
}
