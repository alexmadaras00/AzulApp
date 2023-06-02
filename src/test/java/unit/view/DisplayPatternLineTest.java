package unit.view;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.TileColor;
import view.DisplayPatternLine;

public class DisplayPatternLineTest {
    private DisplayPatternLine displayPatternLine;

    @BeforeEach
    public void setUp() {
        displayPatternLine = new DisplayPatternLine();
    }

    @Test
    public void testEmptyPatternLine() {
        assertEquals("_\n__\n___\n____\n_____", displayPatternLine.toString());
        List<String> result = displayPatternLine.toStringList();
        assertEquals(5, result.size());
        assertEquals("_", result.get(0));
        assertEquals("__", result.get(1));
        assertEquals("___", result.get(2));
        assertEquals("____", result.get(3));
        assertEquals("_____", result.get(4));
    }

    @Test
    public void testsingleTilePatternLine() {
        displayPatternLine.addTile(2, TileColor.RED);
        assertEquals("_\n__\nRrr\n____\n_____", displayPatternLine.toString());
        List<String> result = displayPatternLine.toStringList();
        assertEquals(5, result.size());
        assertEquals("_", result.get(0));
        assertEquals("__", result.get(1));
        assertEquals("Rrr", result.get(2));
        assertEquals("____", result.get(3));
        assertEquals("_____", result.get(4));

    }

    @Test
    public void testMultipleTilePatternLine() {
        displayPatternLine.addTile(0, TileColor.RED);
        displayPatternLine.addTile(2, TileColor.BLUE);
        displayPatternLine.addTile(2, TileColor.BLUE);

        assertEquals("R\n__\nBBb\n____\n_____", displayPatternLine.toString());
        List<String> result = displayPatternLine.toStringList();
        assertEquals(5, result.size());
        assertEquals("R", result.get(0));
        assertEquals("__", result.get(1));
        assertEquals("BBb", result.get(2));
        assertEquals("____", result.get(3));
        assertEquals("_____", result.get(4));
    }

    @Test
    public void testRemoveTilePatternLine() {
        displayPatternLine.addTile(0, TileColor.RED);
        displayPatternLine.addTile(2, TileColor.BLUE);
        displayPatternLine.addTile(2, TileColor.BLUE);
        displayPatternLine.removeTile(2);

        assertEquals("R\n__\nBbb\n____\n_____", displayPatternLine.toString());
        List<String> result = displayPatternLine.toStringList();
        assertEquals(5, result.size());
        assertEquals("R", result.get(0));
        assertEquals("__", result.get(1));
        assertEquals("Bbb", result.get(2));
        assertEquals("____", result.get(3));
        assertEquals("_____", result.get(4));
    }

    @Test
    public void testClearTilePatternLine() {
        displayPatternLine.addTile(0, TileColor.RED);
        displayPatternLine.addTile(2, TileColor.BLUE);
        displayPatternLine.addTile(2, TileColor.BLUE);
        displayPatternLine.clearRow(2);

        assertEquals("R\n__\n___\n____\n_____", displayPatternLine.toString());
        List<String> result = displayPatternLine.toStringList();
        assertEquals(5, result.size());
        assertEquals("R", result.get(0));
        assertEquals("__", result.get(1));
        assertEquals("___", result.get(2));
        assertEquals("____", result.get(3));
        assertEquals("_____", result.get(4));
    }

    @Test
    public void testClearPatternLine() {
        displayPatternLine.addTile(0, TileColor.RED);
        displayPatternLine.clear();

        assertEquals("_\n__\n___\n____\n_____", displayPatternLine.toString());
        List<String> result = displayPatternLine.toStringList();
        assertEquals(5, result.size());
        assertEquals("_", result.get(0));
        assertEquals("__", result.get(1));
        assertEquals("___", result.get(2));
        assertEquals("____", result.get(3));
        assertEquals("_____", result.get(4));
    }
}
