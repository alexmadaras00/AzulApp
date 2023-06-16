package unit.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.TileColor;
import model.factory.FactoryCreator;
import model.factory.FactoryInterface;
import model.factory.FactoryTwinteamWrapper;
import model.factory.TwinteamFactoryCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTwinteamWrapperTest {
       private static FactoryCreator factoryCreator = new TwinteamFactoryCreator();
    private FactoryInterface factory;
    private List<TileColor> tiles;

    @BeforeEach
    public void setUp() {
        factory = factoryCreator.createFactory();
        tiles = new ArrayList<>(List.of(TileColor.BLUE, TileColor.BLUE, TileColor.YELLOW, TileColor.RED));
        Collections.sort(tiles);
    }

    @Test
    public void testCreation() {
        factory = factoryCreator.createFactory();
        assertEquals(true, factory instanceof FactoryTwinteamWrapper);
    }

    // In each round there are no more than 4 tiles on a factory
    @Test
    public void correctSize() {
        assertTrue(factory.getAllTiles().size() <= 4);
        assertTrue(tiles.size() == 4);
    }

    @Test
    public void testAddTiles() {
        factory.addTiles(tiles);
        List<TileColor> getTiles = factory.getAllTiles();
        Collections.sort(getTiles);
        assertEquals(tiles, getTiles);
    }

    @Test
    public void testPopTiles() {
        factory.addTiles(tiles);
        List<TileColor> blueTiles = factory.popTiles(TileColor.BLUE);
        assertEquals(2, blueTiles.size());
        assertEquals(TileColor.BLUE, blueTiles.get(0));
        assertEquals(TileColor.BLUE, blueTiles.get(1));
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
        assertTrue(factory.hasTiles(TileColor.RED));
        assertFalse(factory.hasTiles(TileColor.BLACK));
    }
}
