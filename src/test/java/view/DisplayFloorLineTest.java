package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import model.TileColor;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class DisplayFloorLineTest extends JavaFXApplicationTest {
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
