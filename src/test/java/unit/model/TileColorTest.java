package unit.model;

import model.TileColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TileColorTest {

    @Test
    public void testValueComparison() {
        TileColor red = TileColor.RED;
        TileColor blue = TileColor.BLUE;
        TileColor cyan = TileColor.CYAN;
        TileColor black = TileColor.BLACK;
        TileColor yellow = TileColor.YELLOW;

        // Assert equality
        assertEquals(TileColor.RED, red);
        assertEquals(TileColor.BLUE, blue);
        assertEquals(TileColor.CYAN, cyan);
        assertEquals(TileColor.BLACK, black);
        assertEquals(TileColor.YELLOW, yellow);

        // Assert inequality
        assertNotEquals(TileColor.RED, blue);
        assertNotEquals(TileColor.CYAN, black);
        assertNotEquals(TileColor.RED, cyan);
        assertNotEquals(TileColor.BLUE, yellow);
        assertNotEquals(TileColor.YELLOW, red);
    }
}
