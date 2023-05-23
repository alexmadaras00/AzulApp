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
        displayFactory.tiles.set(0, new DisplayTile(Color.RED));
        assertEquals("R_\n__", displayFactory.toString());
        List<String> result = displayFactory.toStringList();
        assertEquals(2, result.size());
        assertEquals("R_", result.get(0));
        assertEquals("__", result.get(1));

    }

    @Test
    public void testMultipleTileFactory() {
        displayFactory.tiles.set(0, new DisplayTile(Color.RED));
        displayFactory.tiles.set(1, new DisplayTile(Color.CYAN));
        displayFactory.tiles.set(2, new DisplayTile(Color.BLUE));

        assertEquals("RB\nC_", displayFactory.toString());
        List<String> result = displayFactory.toStringList();
        assertEquals(2, result.size());
        assertEquals("RB", result.get(0));
        assertEquals("C_", result.get(1));
    }
}
