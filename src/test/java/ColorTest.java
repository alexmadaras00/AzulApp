import model.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ColorTest {

    @Test
    public void testValueComparison() {
        Color red = Color.RED;
        Color blue = Color.BLUE;
        Color cyan = Color.CYAN;
        Color black = Color.BLACK;
        Color yellow = Color.YELLOW;

        // Assert equality
        assertEquals(Color.RED, red);
        assertEquals(Color.BLUE, blue);
        assertEquals(Color.CYAN, cyan);
        assertEquals(Color.BLACK, black);
        assertEquals(Color.YELLOW, yellow);

        // Assert inequality
        assertNotEquals(Color.RED, blue);
        assertNotEquals(Color.CYAN, black);
        assertNotEquals(Color.RED, cyan);
        assertNotEquals(Color.BLUE, yellow);
        assertNotEquals(Color.YELLOW, red);
    }
}
