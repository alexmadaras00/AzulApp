package unit.model;

import model.TileColor;
import model.Middle;
import model.PlayerTile;
import model.Tile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class MiddleTest {
    static Middle middle;
    static List<Tile> tiles;

    @BeforeEach
    public void setUp() {
        middle = new Middle();
        tiles = List.of(TileColor.BLUE, TileColor.YELLOW, TileColor.YELLOW);
    }

    @Test
    public void testGetAllTiles(){
        middle.addTiles(tiles);
        assertEquals(tiles,middle.getAllTiles());
    }
    @Test
    public void testGetTiles() {
        middle.addTiles(tiles);
        List<Tile> yellowTiles = middle.getTiles(TileColor.YELLOW);
        assertEquals(2,yellowTiles.size());
        assertEquals(TileColor.YELLOW,yellowTiles.get(0));
        assertEquals(TileColor.YELLOW,yellowTiles.get(1));
    }
    @Test
    public void testAddTiles(){
        middle.addTiles(tiles);
        assertEquals(3, middle.getAllTiles().size());
        List<Tile> newTiles = List.of(TileColor.RED);
        middle.addTiles(newTiles);
        assertEquals(4,middle.getAllTiles().size());
        assertEquals(TileColor.RED,middle.getAllTiles().get(3));
    }

    @Test
    public void testPopTiles() {
        middle.addTiles(tiles);
        List<Tile> blueTiles = middle.popTiles(TileColor.YELLOW);
        assertEquals(2, blueTiles.size());
        assertEquals(TileColor.YELLOW, blueTiles.get(0));
        assertEquals(TileColor.YELLOW, blueTiles.get(1));
    }

    @Test
    public void testpopAllTiles() {
        middle.addTiles(tiles);
        assertEquals(3, middle.getAllTiles().size());
        List<Tile> poppedTiles = middle.popAllTiles();
        assertEquals(3, poppedTiles.size());
        assertEquals(0, middle.getAllTiles().size());
    }
    
    @Test
    public void testHasTiles() {
        middle.addTiles(tiles);
        assertTrue(middle.hasTiles(TileColor.BLUE));
        assertFalse(middle.hasTiles(TileColor.BLACK));
    }

    @Test
    public void testPopPlayerTile() {
        middle.addTiles(List.of(PlayerTile.getInstance()));
        assertEquals(PlayerTile.getInstance(), middle.popPlayerTile());
        assertEquals(null, middle.popPlayerTile());
    }

    @Test
    public void testHasPlayerTile() {
        assertFalse(middle.hasPlayerTile());
        middle.addTiles(List.of(PlayerTile.getInstance()));
        assertTrue(middle.hasPlayerTile());
    }

}
