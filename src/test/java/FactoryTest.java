import model.Color;
import model.Factory;
import model.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTest {
    static Factory factory = new Factory();
    @BeforeAll
    public static void setUp() {
        List<Tile> tiles = List.of(Color.BLUE,Color.BLUE,Color.YELLOW,Color.RED);
        factory.addTiles(tiles);
    }
    //In each round there are no more than 4 tiles on a factory
    @Test
    public void correctSize(){
        assertTrue(factory.getAllTiles().size()<=4);
    }
    @Test
    public void testGetAllTiles(){
        List<Tile> tiles = List.of(Color.BLUE,Color.BLUE,Color.YELLOW,Color.RED);
        assertEquals(tiles,factory.getAllTiles());
    }
    @Test
    public void testGetTiles() {
        List<Tile> blueTiles = factory.getTiles(Color.BLUE);
        assertEquals(2, blueTiles.size());
        assertEquals(Color.BLUE, blueTiles.get(0));
        assertEquals(Color.BLUE, blueTiles.get(1));
    }
    @Test
    public void testAddTiles(){
        assertTrue(factory.getAllTiles().size()<=4);
        factory.getAllTiles().clear();
        List<Tile> newTiles = new ArrayList<>();
        newTiles.add(Color.CYAN);
        newTiles.add(Color.RED);
        factory.addTiles(newTiles);
        assertEquals(2,factory.getAllTiles().size());
        assertEquals(Color.CYAN,factory.getAllTiles().get(0));
        assertEquals(Color.RED,factory.getAllTiles().get(1));
    }
    @Test
    public void testHasTiles(){
        boolean isHavingRedTile = factory.hasTiles(Color.RED);
        boolean isHavingBlackTile = factory.hasTiles(Color.BLACK);
        assertTrue(isHavingRedTile);
        assertFalse(isHavingBlackTile);
    }
}
