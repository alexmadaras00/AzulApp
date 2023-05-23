import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Color;
import view.DisplayPatternLine;
import view.DisplayTile;

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
        displayPatternLine.tiles.get(2).set(0,new DisplayTile(Color.RED));
        assertEquals("_\n__\nR__\n____\n_____", displayPatternLine.toString());
        List<String> result = displayPatternLine.toStringList();
        assertEquals(5, result.size());
        assertEquals("_", result.get(0));
        assertEquals("__", result.get(1));
        assertEquals("R__", result.get(2));
        assertEquals("____", result.get(3));
        assertEquals("_____", result.get(4));

    }

    @Test
    public void testMultipleTilePatternLine() {
        displayPatternLine.tiles.get(0).set(0,new DisplayTile(Color.RED));
        displayPatternLine.tiles.get(2).set(0,new DisplayTile(Color.BLUE));
        displayPatternLine.tiles.get(3).set(2,new DisplayTile(Color.CYAN));

        assertEquals("R\n__\nB__\n__C_\n_____", displayPatternLine.toString());
        List<String> result = displayPatternLine.toStringList();
        assertEquals(5, result.size());
        assertEquals("R", result.get(0));
        assertEquals("__", result.get(1));
        assertEquals("B__", result.get(2));
        assertEquals("__C_", result.get(3));
        assertEquals("_____", result.get(4));
    }
}
