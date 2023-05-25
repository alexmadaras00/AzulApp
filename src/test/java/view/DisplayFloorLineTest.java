package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.TileColor;

public class DisplayFloorLineTest extends FXTest {
    private DisplayFloorLine displayFloorLine;

    @BeforeEach
    public void setUp() {
        displayFloorLine = new DisplayFloorLine();
    }

    @Test
    public void testConstructorFloorLine() {
        assertDoesNotThrow(() -> {
            displayFloorLine = new DisplayFloorLine();
        });
    }

    @Test
    public void testAddTileFactory() {
        assertDoesNotThrow(() -> {
            displayFloorLine.addTile(TileColor.RED);
        });
    }

    @Test
    public void testRemoveTileFactory() {
        displayFloorLine.addTile(TileColor.RED);
        assertDoesNotThrow(() -> {
            displayFloorLine.removeTiles(TileColor.RED);
        });

    }

    @Test
    public void testClearFactory() {
        displayFloorLine.addTile(TileColor.RED);
        assertDoesNotThrow(() -> {
            displayFloorLine.clear();
        });
    }
}
