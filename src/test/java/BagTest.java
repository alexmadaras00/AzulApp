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

    @BeforeAll
    public static void setUp() {
        bag = new Bag();
        tiles.add(Color.BLUE);
        tiles.add(Color.BLUE);
        tiles.add(Color.YELLOW);
        tiles.add(Color.RED);
        assertTrue(bag.getTiles().size() <= 100);
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
        newTiles.add(Color.CYAN);
        newTiles.add(Color.RED);
        bag.addTiles(newTiles);
        assertEquals(6, bag.getTiles().size());
        assertEquals(Color.CYAN, bag.getTiles().get(4));
        assertEquals(Color.RED, bag.getTiles().get(5));
    }
    @Test
    public void testPopTiles() {
        int count = 2;
        assertTrue(tiles.size() >= count);
        System.out.println(bag.getTiles());
        List<Tile> poppedTiles = bag.popTiles(count);
        System.out.println(bag.getTiles());
        assertEquals(4, bag.getTiles().size());
        assertEquals(Color.RED, tiles.get(tiles.size() - 1));
        assertEquals(tiles, poppedTiles);
    }


}
