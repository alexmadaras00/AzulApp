package unit.model;

import model.PlayerTile;
import model.TileColor;
import model.FloorLine;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FloorLineTest {
    static FloorLine floorLine;
    static List<Tile> excessTiles;
    @BeforeEach
    public void setUp() {
        floorLine = new FloorLine();
        excessTiles = new ArrayList<>();
        excessTiles.add(TileColor.RED);
        excessTiles.add(TileColor.RED);
        excessTiles.add(TileColor.RED);
        excessTiles.add(TileColor.RED);
        floorLine.addTiles(excessTiles);
    }

    @Test
    void ShouldBeImmutable() {
        List<Tile> copiedTiles = floorLine.getCopyTiles();
        assertThrows(UnsupportedOperationException.class,()->copiedTiles.set(4, TileColor.RED));
    }


    @Test
    public void testGetAllTiles(){
        List<Tile> theTiles = floorLine.getCopyTiles();
        assertEquals(excessTiles, theTiles);
        assertTrue(floorLine.getCopyTiles().size() <= 7);
    }

    @Test
    public void testFullFloorLine(){
        excessTiles = new ArrayList<>();
        excessTiles.add(TileColor.BLUE);
        excessTiles.add(TileColor.BLUE);
        excessTiles.add(TileColor.BLUE);
        floorLine.addTiles(excessTiles);
        excessTiles.add(PlayerTile.getInstance());
        assertTrue(floorLine.addTiles(excessTiles).size()>0);
        assertTrue(floorLine.getCopyTiles().size() <= 7);
        assertTrue(floorLine.getScore()>= -14);
    }
    @Test
    public void testClearTiles(){
        assertEquals(floorLine.clearTiles(),excessTiles);
    }
}