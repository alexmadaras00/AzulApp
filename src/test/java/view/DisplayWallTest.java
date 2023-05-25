package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.TileColor;
import model.Tile;

public class DisplayWallTest extends FXTest {
    private DisplayWall displayWall;

    public static List<List<Tile>> wallPattern() {
        List<List<Tile>> wallTemplate = new ArrayList<List<Tile>>();
        List<TileColor> colors = Arrays.asList(TileColor.values());
        for (int i = 0; i < colors.size(); i++) {
            List<Tile> wallLine = new ArrayList<Tile>();
            for (int j = 0; j < colors.size(); j++) {
                TileColor color = colors.get((j - i + colors.size()) % colors.size());
                wallLine.add(color);
            }
            wallTemplate.add(wallLine);
        }
        return wallTemplate;
    }

    @BeforeEach
    public void setUp() {
        displayWall = new DisplayWall(DisplayWallTest.wallPattern());
    }
    @Test
    public void testConstructorWall() {
        assertDoesNotThrow(() -> {
            displayWall = new DisplayWall(DisplayWallTest.wallPattern());
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
