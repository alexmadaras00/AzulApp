import model.Bag;
import model.Color;
import model.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BagTest {
    static Bag bag;
    static List<Tile> tiles = new ArrayList<>();

    @Test
    public void testGetTiles() {
        bag.addTiles(tiles);
        assertEquals(tiles, bag.getTiles());
        assertTrue(bag.getTiles().size() <= 100);
    }

    @Test
    public void testAddTiles() {
        bag = new Bag();
        tiles.add(Color.BLUE);
        tiles.add(Color.BLUE);
        tiles.add(Color.YELLOW);
        tiles.add(Color.RED);
        assertTrue(bag.getTiles().size() <= 100);
        List<Tile> newTiles = new ArrayList<>();
        newTiles.add(Color.CYAN);
        newTiles.add(Color.RED);
        bag.addTiles(newTiles);
        assertEquals(6, bag.getTiles().size());
        assertEquals(Color.CYAN, bag.getTiles().get(4));
        assertEquals(Color.RED, bag.getTiles().get(5));
    }
    @Test
    public void testPopTiles() {
        int count = 3;
        bag = new Bag();
        tiles.add(Color.BLUE);
        tiles.add(Color.BLUE);
        tiles.add(Color.YELLOW);
        tiles.add(Color.RED);
        bag.addTiles(tiles);
        List<Tile> tilesBag = bag.getTiles();
        //Before popping the tiles
        assertTrue(bag.getTiles().size() <= 100);
        assertTrue(tiles.size() >= count);
        //When popping the tiles (poppedList)
        List<Tile> poppedTiles = bag.popTiles(count);
        assertEquals(1, tilesBag.size());
        assertEquals(Color.BLUE, tilesBag.get(tilesBag.size() - 1));
        //After popping the tiles
        assertEquals(3, poppedTiles.size());
        assertEquals(Color.RED, poppedTiles.get(0));
        assertEquals(Color.YELLOW, poppedTiles.get(1));
        assertEquals(Color.BLUE, poppedTiles.get(2));
    }


}
