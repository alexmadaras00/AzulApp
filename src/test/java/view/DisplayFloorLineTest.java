package view;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Color;

public class DisplayFloorLineTest {
    private DisplayFloorLine displayFloorLine;

    @BeforeEach
    public void setUp() {
        displayFloorLine = new DisplayFloorLine();
    }

    @Test
    public void testEmptyFloorLine() {
        assertEquals("", displayFloorLine.toString());
        List<String> result = displayFloorLine.toStringList();
        assertEquals(1, result.size());
        assertEquals("", result.get(0));
    }

    @Test
    public void testsingleTileFloorLine() {
        displayFloorLine.addTile(new DisplayTile(Color.RED));
        assertEquals("R", displayFloorLine.toString());
        List<String> result = displayFloorLine.toStringList();
        assertEquals(1, result.size());
        assertEquals("R", result.get(0));

    }

    @Test
    public void testMultipleTileFloorLine() {
        displayFloorLine.addTile(new DisplayTile(Color.RED));
        displayFloorLine.addTile(new DisplayTile(Color.CYAN));
        displayFloorLine.addTile(new DisplayTile(Color.BLUE));

        assertEquals("RCB", displayFloorLine.toString());
        List<String> result = displayFloorLine.toStringList();
        assertEquals(1, result.size());
        assertEquals("RCB", result.get(0));
    }

    @Test
    public void testRemoveTileFloorLine() {
        displayFloorLine.addTile(new DisplayTile(Color.RED));
        displayFloorLine.addTile(new DisplayTile(Color.CYAN));
        displayFloorLine.addTile(new DisplayTile(Color.BLUE));
        assertEquals("RCB", displayFloorLine.toString());
        displayFloorLine.removeTiles(Color.BLUE);
        assertEquals("RC", displayFloorLine.toString());
        List<String> result = displayFloorLine.toStringList();
        assertEquals(1, result.size());
        assertEquals("RC", result.get(0));
    }
}
