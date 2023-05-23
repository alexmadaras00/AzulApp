package view;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Color;
import model.Wall;
import view.DisplayPatternLine;
import view.DisplayWall;

public class DisplayWallTest {
    private DisplayWall displayWall;
    private Wall wall;
    @BeforeEach
    public void setUp() {
        displayWall = new DisplayWall();
        wall = new Wall();
    }

    @Test
    public void testWallPattern() {
        for (int row = 0; row < displayWall.wall.size(); row++) {
            for (int column = 0; column < displayWall.wall.size(); column++) {
                assertEquals(wall.getTemplateColor(row, column), displayWall.wall.get(row).get(column).tile);
            }
        }
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
        displayWall.wall.get(0).get(0).isPlaceholder = false;
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
        displayWall.wall.get(0).get(0).isPlaceholder = false;
        displayWall.wall.get(2).get(3).isPlaceholder = false;
        displayWall.wall.get(1).get(4).isPlaceholder = false;
        displayWall.wall.get(0).get(3).isPlaceholder = false;
        assertEquals("RbcBy\nyrbcB\nbyrBc\ncbyrb\nbcbyr", displayWall.toString());
        List<String> result = displayWall.toStringList();
        assertEquals(5, result.size());
        assertEquals("RbcBy", result.get(0));
        assertEquals("yrbcB", result.get(1));
        assertEquals("byrBc", result.get(2));
        assertEquals("cbyrb", result.get(3));
        assertEquals("bcbyr", result.get(4));
    }
}
