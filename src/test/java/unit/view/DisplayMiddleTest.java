package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import model.TileColor;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class DisplayMiddleTest extends JavaFXApplicationTest {
    private DisplayMiddle displayMiddle;
    
    
    @BeforeEach
    public void setUp() {
        displayMiddle = new DisplayMiddle();
    }

    @Test
    public void testConstructorMiddle() {
        assertDoesNotThrow(() -> {
            displayMiddle = new DisplayMiddle();
        });
    }

    @Test
    public void testAddTileMiddle() {
        assertDoesNotThrow(() -> {
            displayMiddle.addTile(TileColor.RED);
        });
    }

    @Test
    public void testRemoveTileMiddle() {
        displayMiddle.addTile(TileColor.RED);
        assertDoesNotThrow(() -> {
            displayMiddle.removeTiles(TileColor.RED);
        });

    }

    @Test
    public void testClearMiddle() {
        displayMiddle.addTile(TileColor.RED);
        assertDoesNotThrow(() -> {
            displayMiddle.clear();
        });
    }
}
