package unit.model;

import model.Color;
import model.Factory;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTest {
    private static Factory factory;
    private static List<Tile> tiles;

    @BeforeEach
    public  void setUp() {
        factory = new Factory();
         tiles = List.of(Color.BLUE, Color.BLUE, Color.YELLOW, Color.RED);
    }

    // In each round there are no more than 4 tiles on a factory
    @Test
    public void correctSize() {
        assertTrue(factory.getAllTiles().size() <= 4);
    }

    @Test
    public void testAddTiles() {
        factory.addTiles(tiles);
        assertEquals(tiles, factory.getAllTiles());
    }

    @Test
    public void testPopTiles() {
        factory.addTiles(tiles);
        List<Tile> blueTiles = factory.popTiles(Color.BLUE);
        assertEquals(2, blueTiles.size());
        assertEquals(Color.BLUE, blueTiles.get(0));
        assertEquals(Color.BLUE, blueTiles.get(1));
    }

    @Test
    public void testpopAllTiles() {
        factory.addTiles(tiles);
        assertEquals(4, factory.getAllTiles().size());
        factory.popAllTiles();
        assertEquals(0, factory.getAllTiles().size());
    }

    @Test
    public void testHasTiles() {
        factory.addTiles(tiles);
        assertTrue(factory.hasTiles(Color.RED));
        assertFalse(factory.hasTiles(Color.BLACK));
    }
}
