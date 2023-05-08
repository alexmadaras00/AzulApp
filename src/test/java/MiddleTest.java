import model.Color;
import model.Middle;
import model.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MiddleTest {
    Middle middle = new Middle();
    List<Tile> tiles = List.of(Color.BLUE,Color.YELLOW,Color.YELLOW,Color.RED);
    @Before
    public void setUp() {
        middle.addTiles(tiles);
    }
    @Test
    public void testGetAllTiles(){
        assertEquals(tiles,middle.getAllTiles());
    }
    @Test
    public void testGetTiles() {
        List<Tile> yellowTiles = middle.getTiles(Color.YELLOW);
        assertEquals(2,yellowTiles.size());
        assertEquals(Color.YELLOW,yellowTiles.get(0));
        assertEquals(Color.YELLOW,yellowTiles.get(1));
    }
    @Test
    public void testAddTiles(){
        assertTrue(middle.getAllTiles().size()<=4);
        List<Tile> newTiles = new ArrayList<>();
        newTiles.add(Color.RED);
        newTiles.add(Color.RED);
        middle.addTiles(newTiles);
        assertEquals(6,middle.getAllTiles().size());
        assertEquals(Color.RED,middle.getAllTiles().get(4));
        assertEquals(Color.RED,middle.getAllTiles().get(5));
    }
}
