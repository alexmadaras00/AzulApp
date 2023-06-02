package unit.view;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.TileColor;
import model.PlayerTile;
import view.DisplayTile;

public class DisplayTileTest {
    private DisplayTile displayTile;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testColorStrings() {
        List<TileColor> tileColors = Arrays.asList(TileColor.values());
        for (TileColor tileColor : tileColors) {
            displayTile = new DisplayTile(tileColor);
            assertEquals(tileColor.toString().substring(0, 1), displayTile.toString());
            List<String> result = displayTile.toStringList();
            assertEquals(1, result.size());
            assertEquals(tileColor.toString().substring(0, 1), result.get(0));

        }
    }

    @Test
    public void testPlaceholderStrings() {
        List<TileColor> tileColors = Arrays.asList(TileColor.values());
        for (TileColor tileColor : tileColors) {
            displayTile = new DisplayTile(tileColor, false);
            assertEquals(tileColor.toString().substring(0, 1).toLowerCase(), displayTile.toString());
            List<String> result = displayTile.toStringList();
            assertEquals(1, result.size());
            assertEquals(tileColor.toString().substring(0, 1).toLowerCase(), result.get(0));

        }
    }

    @Test
    public void testPlayerStrings() {
        displayTile = new DisplayTile(PlayerTile.getInstance());
        assertEquals("1", displayTile.toString());
        List<String> result = displayTile.toStringList();
        assertEquals(1, result.size());
        assertEquals("1", result.get(0));

    }
}
