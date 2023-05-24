package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Color;

public class DisplayWallTest {
    private DisplayWall displayWall;

    @BeforeEach
    public void setUp() {
        displayWall = new DisplayWall();
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
        displayWall.setTile(0, Color.RED, true);
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
        displayWall.setTile(0, Color.RED, true);
        displayWall.setTile(2, Color.BLUE, true);
        displayWall.setTile(0, Color.BLACK, true);
        displayWall.setTile(1, Color.BLACK, true);

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
