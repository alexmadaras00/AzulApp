package unit.view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import javafxHelpers.JavaFXApplicationTest;
import model.TileColor;
import view.DisplayFactory;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class DisplayFactoryTest extends JavaFXApplicationTest {
    private DisplayFactory displayFactory;
    private int id;

    @BeforeEach
    public void setUp() {
        id = 1;
        displayFactory = new DisplayFactory(id);
    }

    @Test
    public void testIdFactory() {
        assertEquals(id, displayFactory.id);
    }

    @Test
    public void testConstructorFactory() {
        assertDoesNotThrow(() -> {
            displayFactory = new DisplayFactory(id);
        });
    }

    @Test
    public void testAddTileFactory() {
        assertDoesNotThrow(() -> {
            displayFactory.addTile(TileColor.RED);
        });
    }

    @Test
    public void testRemoveTileFactory() {
        displayFactory.addTile(TileColor.RED);
        assertDoesNotThrow(() -> {
            displayFactory.removeTiles(TileColor.RED);
        });

    }

    @Test
    public void testClearFactory() {
        displayFactory.addTile(TileColor.RED);
        assertDoesNotThrow(() -> {
            displayFactory.clear();
        });
    }
}
