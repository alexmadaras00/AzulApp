package view;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Color;
import view.DisplayMiddle;
import view.DisplayTile;

public class DisplayMiddleTest {
    private DisplayMiddle displayMidde;

    @BeforeEach
    public void setUp() {
        displayMidde = new DisplayMiddle();
    }

    @Test
    public void testEmptyFactory() {
        assertEquals("\n", displayMidde.toString());
        List<String> result = displayMidde.toStringList();
        assertEquals(2, result.size());
        assertEquals("", result.get(0));
        assertEquals("", result.get(1));
    }

    @Test
    public void testsingleTileFactory() {
        displayMidde.tiles.add(new DisplayTile(Color.RED));
        assertEquals("R\n", displayMidde.toString());
        List<String> result = displayMidde.toStringList();
        assertEquals(2, result.size());
        assertEquals("R", result.get(0));
        assertEquals("", result.get(1));
    }

    @Test
    public void testMultipleTileFactory() {
        displayMidde.tiles.add(new DisplayTile(Color.RED));
        displayMidde.tiles.add(new DisplayTile(Color.CYAN));
        displayMidde.tiles.add(new DisplayTile(Color.BLUE));
        displayMidde.tiles.add(new DisplayTile(Color.BLUE));
        displayMidde.tiles.add(new DisplayTile(Color.CYAN));

        assertEquals("RBC\nCB", displayMidde.toString());
        List<String> result = displayMidde.toStringList();
        assertEquals(2, result.size());
        assertEquals("RBC", result.get(0));
        assertEquals("CB", result.get(1));
    }
}
