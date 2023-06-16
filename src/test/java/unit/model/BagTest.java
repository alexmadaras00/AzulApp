package unit.model;

import model.Bag;
import model.TileColor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BagTest {
    static Bag bag;
    static List<TileColor> tiles;

    @BeforeEach
    public void setUp() {
        bag = new Bag();
        tiles = new ArrayList<>();
        tiles.add(TileColor.BLUE);
        tiles.add(TileColor.BLUE);
        tiles.add(TileColor.YELLOW);
        tiles.add(TileColor.RED);
        Collections.sort(tiles);
    }

    @Test
    public void testGetTiles() {
        bag.addTiles(tiles);
        List<TileColor> bagTiles = bag.getTiles();
        Collections.sort(bagTiles);
        assertEquals(tiles, bagTiles);
        assertTrue(bag.getTiles().size() <= 100);
    }

    @Test
    public void testAddTiles() {
        tiles.add(TileColor.CYAN);
        tiles.add(TileColor.RED);
        bag.addTiles(tiles);
        assertEquals(6, bag.getTiles().size());

        List<TileColor> bagTiles = bag.getTiles();
        Collections.sort(bagTiles);
        Collections.sort(tiles);

        assertEquals(tiles, bagTiles);
    }

    @Test
    public void testPopTiles() {
        bag.addTiles(tiles);
        TileColor popedTile = bag.popTiles(1).get(0);
        assertEquals(3, bag.getTiles().size());
        List<TileColor> restTiles = bag.getTiles();
        restTiles.add(popedTile);
        Collections.sort(restTiles);
        assertEquals(tiles, restTiles);

    }

    @Test
    public void testPopTilesMultiple() {
        bag.addTiles(tiles);
        List<TileColor> popedTiles = bag.popTiles(2);
        assertEquals(2, bag.getTiles().size());
        List<TileColor> restTiles = bag.getTiles();
        restTiles.addAll(popedTiles);
        Collections.sort(restTiles);
        assertEquals(tiles, restTiles);

    }

}
