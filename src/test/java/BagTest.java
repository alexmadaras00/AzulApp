import model.Bag;
import model.Color;
import model.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BagTest {
    Bag bag = new Bag();
    List<Tile> tiles = new ArrayList<>();
    @Before
    public void setUp() {
        tiles.add(Color.BLUE);
        tiles.add(Color.BLUE);
        tiles.add(Color.YELLOW);
        tiles.add(Color.RED);
    }
    @Test
    public void testGetTiles() {
        bag.addTiles(tiles);
        assertEquals(tiles, bag.getTiles());
    }
    @Test
    public void testPopTiles() {
        int count = 2;
        assertTrue(tiles.size() >= count);
        bag.addTiles(tiles);
        System.out.println(bag.getTiles());
        List<Tile> poppedTiles = bag.popTiles(count);
        tiles.remove(tiles.size()-1);
        tiles.remove(tiles.size()-1);
        assertEquals(Color.BLUE, tiles.get(tiles.size() - 1));
        assertEquals(tiles, poppedTiles);
    }
    @Test
    public void testAddTiles(){
        List<Tile> newTiles = new ArrayList<>();
        newTiles.add(Color.CYAN);
        newTiles.add(Color.RED);
        bag.addTiles(newTiles);
        assertEquals(2, bag.getTiles().size());
        assertEquals(Color.CYAN, bag.getTiles().get(0));
        assertEquals(Color.RED, bag.getTiles().get(1));
    }


}
