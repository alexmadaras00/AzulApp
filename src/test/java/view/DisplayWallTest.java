package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.TileColor;
import model.Wall;

public class DisplayWallTest extends FXTest {
    private DisplayWall displayWall;

    

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
