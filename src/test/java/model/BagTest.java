package model;

import model.Bag;
import model.TileColor;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BagTest {
    static Bag bag;
    static List<Tile> tiles;

    @BeforeEach
    public void setUp() {
        bag = new Bag();
        tiles = new ArrayList<>();
        tiles.add(TileColor.BLUE);
        tiles.add(TileColor.BLUE);
        tiles.add(TileColor.YELLOW);
        tiles.add(TileColor.RED);
    }

    @Test
    public void testGetTiles() {
        bag.addTiles(tiles);
        assertEquals(tiles, bag.getTiles());
        assertTrue(bag.getTiles().size() <= 100);
    }

    @Test
    public void testAddTiles() {
        List<Tile> newTiles = new ArrayList<>();
        newTiles.add(TileColor.CYAN);
        newTiles.add(TileColor.RED);
        bag.addTiles(tiles);
        bag.addTiles(newTiles);
        assertEquals(6, bag.getTiles().size());
        assertEquals(TileColor.CYAN, bag.getTiles().get(4));
        assertEquals(TileColor.RED, bag.getTiles().get(5));
    }

    @Test
    public void testPopTiles() {
        bag.addTiles(tiles);
        int count = 3;
        List<Tile> tilesBag = bag.getTiles();
        // Before popping the tiles
        assertTrue(bag.getTiles().size() <= 100);
        assertTrue(tiles.size() >= count);
        // When popping the tiles (poppedList)
        List<Tile> poppedTiles = bag.popTiles(count);
        assertEquals(1, tilesBag.size());
        assertEquals(TileColor.BLUE, tilesBag.get(tilesBag.size() - 1));
        // After popping the tiles
        assertEquals(3, poppedTiles.size());
        assertEquals(TileColor.RED, poppedTiles.get(0));
        assertEquals(TileColor.YELLOW, poppedTiles.get(1));
        assertEquals(TileColor.BLUE, poppedTiles.get(2));
    }

}
