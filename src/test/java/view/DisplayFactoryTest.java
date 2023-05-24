package view;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Color;
import view.DisplayFactory;
import view.DisplayTile;

public class DisplayFactoryTest {
    private DisplayFactory displayFactory;

    @BeforeEach
    public void setUp() {
        displayFactory = new DisplayFactory();
    }

    @Test
    public void testEmptyFactory() {
        assertEquals("__\n__", displayFactory.toString());
        List<String> result = displayFactory.toStringList();
        assertEquals(2, result.size());
        assertEquals("__", result.get(0));
        assertEquals("__", result.get(1));
    }

    @Test
    public void testsingleTileFactory() {
        displayFactory.addTile(new DisplayTile(Color.RED));
        assertEquals("R_\n__", displayFactory.toString());
        List<String> result = displayFactory.toStringList();
        assertEquals(2, result.size());
        assertEquals("R_", result.get(0));
        assertEquals("__", result.get(1));

    }

    @Test
    public void testMultipleTileFactory() {
        displayFactory.addTile(new DisplayTile(Color.RED));
        displayFactory.addTile(new DisplayTile(Color.CYAN));
        displayFactory.addTile(new DisplayTile(Color.BLUE));

        assertEquals("RB\nC_", displayFactory.toString());
        List<String> result = displayFactory.toStringList();
        assertEquals(2, result.size());
        assertEquals("RB", result.get(0));
        assertEquals("C_", result.get(1));
    }

    @Test
    public void testRemoveTileFactory() {
        displayFactory.addTile(new DisplayTile(Color.RED));
        displayFactory.addTile(new DisplayTile(Color.CYAN));
        displayFactory.addTile(new DisplayTile(Color.RED));
        assertEquals("RR\nC_", displayFactory.toString());
        displayFactory.removeTiles(Color.RED);
        assertEquals("C_\n__", displayFactory.toString());
        List<String> result = displayFactory.toStringList();
        assertEquals(2, result.size());
        assertEquals("C_", result.get(0));
        assertEquals("__", result.get(1));
    }
}
