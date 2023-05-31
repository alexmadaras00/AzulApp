package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TileColor;
import model.Wall;

public class DisplayWallTest extends ApplicationTest {
    private DisplayWall displayWall;

    @Override
    public void start(Stage stage) {
        displayWall = new DisplayWall(Wall.wallPattern());
        Scene scene = new Scene(displayWall);
        stage.setScene(scene);
        stage.show();
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
