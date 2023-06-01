package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TileColor;

public class DisplayTileTest extends ApplicationTest {
    private DisplayTile displayTile;

    @Override
    public void start(Stage stage) {
        displayTile = new DisplayTile(TileColor.RED);
        Scene scene = new Scene(displayTile);
        stage.setScene(scene);
    }

    @Test
    public void testConstructor() {

        assertDoesNotThrow(() -> {
            displayTile = new DisplayTile(TileColor.RED);
        });
    }

}
