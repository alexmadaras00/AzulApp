package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TileColor;

public class DisplayFactoryTest extends ApplicationTest {
    private DisplayFactory displayFactory;
    private int id;

    @Override
    public void start(Stage stage) {
        displayFactory = new DisplayFactory(id);
        Scene scene = new Scene(displayFactory);
        stage.setScene(scene);
        stage.show();
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
