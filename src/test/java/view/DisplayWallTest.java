package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Color;
import model.Tile;

public class DisplayWallTest {
    private DisplayWall displayWall;

    public static List<List<Tile>> wallPattern() {
        List<List<Tile>> wallTemplate = new ArrayList<List<Tile>>();
        List<Color> colors = Arrays.asList(Color.values());
        for (int i = 0; i < colors.size(); i++) {
            List<Tile> wallLine = new ArrayList<Tile>();
            for (int j = 0; j < colors.size(); j++) {
                Color color = colors.get((j - i + colors.size()) % colors.size());
                wallLine.add(color);
            }
            wallTemplate.add(wallLine);
        }
        return wallTemplate;
    }

    @BeforeEach
    public void setUp() {
        displayWall = new DisplayWall(DisplayWallTest.wallPattern());
    }

    @Test
    public void testEmptyPatternLine() {
        assertEquals("rbcby\nyrbcb\nbyrbc\ncbyrb\nbcbyr", displayWall.toString());
        List<String> result = displayWall.toStringList();
        assertEquals(5, result.size());
        assertEquals("rbcby", result.get(0));
        assertEquals("yrbcb", result.get(1));
        assertEquals("byrbc", result.get(2));
        assertEquals("cbyrb", result.get(3));
        assertEquals("bcbyr", result.get(4));
    }

    @Test
    public void testsingleTilePatternLine() {
        displayWall.addTile(0, Color.RED);
        assertEquals("Rbcby\nyrbcb\nbyrbc\ncbyrb\nbcbyr", displayWall.toString());
        List<String> result = displayWall.toStringList();
        assertEquals(5, result.size());
        assertEquals("Rbcby", result.get(0));
        assertEquals("yrbcb", result.get(1));
        assertEquals("byrbc", result.get(2));
        assertEquals("cbyrb", result.get(3));
        assertEquals("bcbyr", result.get(4));

    }

    @Test
    public void testMultipleTilePatternLine() {
        displayWall.addTile(0, Color.RED);
        displayWall.addTile(2, Color.BLUE);
        displayWall.addTile(0, Color.BLACK);
        displayWall.addTile(1, Color.BLACK);

        assertEquals("RbcBy\nyrbcB\nbyrBc\ncbyrb\nbcbyr", displayWall.toString());
        List<String> result = displayWall.toStringList();
        assertEquals(5, result.size());
        assertEquals("RbcBy", result.get(0));
        assertEquals("yrbcB", result.get(1));
        assertEquals("byrBc", result.get(2));
        assertEquals("cbyrb", result.get(3));
        assertEquals("bcbyr", result.get(4));
    }

    @Test
    public void testRemoveTilePatternLine() {
        displayWall.addTile(0, Color.RED);
        displayWall.addTile(2, Color.BLUE);
        displayWall.addTile(0, Color.BLACK);
        displayWall.addTile(1, Color.BLACK);
        displayWall.removeTile(0, Color.BLACK);

        assertEquals("Rbcby\nyrbcB\nbyrBc\ncbyrb\nbcbyr", displayWall.toString());
        List<String> result = displayWall.toStringList();
        assertEquals(5, result.size());
        assertEquals("Rbcby", result.get(0));
        assertEquals("yrbcB", result.get(1));
        assertEquals("byrBc", result.get(2));
        assertEquals("cbyrb", result.get(3));
        assertEquals("bcbyr", result.get(4));
    }
}
