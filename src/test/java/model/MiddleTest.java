package model;

import model.TileColor;
import model.Middle;
import model.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MiddleTest {
    static Middle middle;
    static List<Tile> tiles = List.of(TileColor.BLUE, TileColor.YELLOW, TileColor.YELLOW);

    @Test
    public void testGetAllTiles() {
        middle = new Middle();
        middle.addTiles(tiles);
        assertEquals(tiles, middle.getAllTiles());
    }

    @Test
    public void testGetTiles() {
        middle = new Middle();
        middle.addTiles(tiles);
        List<Tile> yellowTiles = middle.getTiles(TileColor.YELLOW);
        assertEquals(2, yellowTiles.size());
        assertEquals(TileColor.YELLOW, yellowTiles.get(0));
        assertEquals(TileColor.YELLOW, yellowTiles.get(1));
    }

    @Test
    public void testAddTiles() {

        middle = new Middle();
        middle.addTiles(tiles);
        assertTrue(middle.getAllTiles().size() <= 4);
        List<Tile> newTiles = new ArrayList<>();
        newTiles.add(TileColor.RED);
        middle.addTiles(newTiles);
        assertEquals(4, middle.getAllTiles().size());
        assertEquals(TileColor.RED, middle.getAllTiles().get(3));
        assertTrue(middle.getAllTiles().size() <= 4);
    }
}
