package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TileColor;

public class DisplayFloorLineTest extends ApplicationTest {
    private DisplayFloorLine displayFloorLine;

    @Override
    public void start(Stage stage) {
        displayFloorLine = new DisplayFloorLine();
        Scene scene = new Scene(displayFloorLine);
        stage.setScene(scene);
    }
    
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
