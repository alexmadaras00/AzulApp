package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import model.TileColor;

public class DisplayTileTest extends FXTest {
    private DisplayTile displayTile;

    @Test
    public void testConstructor() {

        assertDoesNotThrow(() -> {
            displayTile = new DisplayTile(TileColor.RED);
        });
    }

}
