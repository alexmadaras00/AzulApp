package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TileColor;

public class DisplayMiddleTest extends ApplicationTest {
    private DisplayMiddle displayMiddle;

    @Override
    public void start(Stage stage) {
        displayMiddle = new DisplayMiddle();
        Scene scene = new Scene(displayMiddle);
        stage.setScene(scene);
        stage.show();
    }
    
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
