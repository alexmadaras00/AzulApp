package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import model.TileColor;

public class DisplayFactoryTest {
    private DisplayFactory displayFactory;
    private int id;
    static {

        System.setProperty("java.awt.headless", "false");
    }

    @BeforeAll
    public static void setupSpec() throws Exception {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
        }
        FxToolkit.registerPrimaryStage();
    }

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
