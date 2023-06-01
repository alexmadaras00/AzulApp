package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import model.TileColor;
import model.Wall;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

public class DisplayWallTest {
    private DisplayWall displayWall;
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
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }

    @BeforeEach
    public void setUp() {
        displayWall = new DisplayWall(Wall.wallPattern());
    }

    @Test
    public void testConstructorWall() {
        assertDoesNotThrow(() -> {
            displayWall = new DisplayWall(Wall.wallPattern());
        });
    }

    @Test
    public void testAddTileWall() {
        assertDoesNotThrow(() -> {
            displayWall.addTile(0, TileColor.RED);
        });
    }

    @Test
    public void testRemoveTileWall() {
        displayWall.addTile(0, TileColor.RED);
        assertDoesNotThrow(() -> {
            displayWall.removeTile(0, TileColor.RED);
        });

    }

    @Test
    public void testClearWall() {
        displayWall.addTile(0, TileColor.RED);
        assertDoesNotThrow(() -> {
            displayWall.clear();
        });
    }
}
