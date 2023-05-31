package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TileColor;

public class DisplayPatternLineTest extends ApplicationTest {
    private DisplayPatternLine displayPatternLine;

    @Override
    public void start(Stage stage) {
        displayPatternLine = new DisplayPatternLine();
        Scene scene = new Scene(displayPatternLine);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        displayPatternLine = new DisplayPatternLine();
    }

    @Test
    public void testConstructorPatternLine() {
        assertDoesNotThrow(() -> {
            displayPatternLine = new DisplayPatternLine();
        });
    }

    @Test
    public void testAddTilePatternLine() {
        assertDoesNotThrow(() -> {
            displayPatternLine.addTile(0, TileColor.RED);
        });
    }

    @Test
    public void testRemoveTilePatternLine() {
        displayPatternLine.addTile(0, TileColor.RED);
        assertDoesNotThrow(() -> {
            displayPatternLine.removeTile(0);
        });

    }

    @Test
    public void testClearPatternLine() {
        displayPatternLine.addTile(0, TileColor.RED);
        assertDoesNotThrow(() -> {
            displayPatternLine.clear();
        });
    }
}
