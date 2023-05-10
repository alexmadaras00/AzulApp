import model.Color;
import model.Factory;
import model.FloorLine;
import model.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FloorLineTest {
    static FloorLine floorLine;
    static List<Tile> excessTiles;
    @BeforeEach
    public void setUp() {
        floorLine = new FloorLine();
        excessTiles = new ArrayList<>();
        excessTiles.add(Color.RED);
        excessTiles.add(Color.RED);
        excessTiles.add(Color.RED);
        excessTiles.add(Color.RED);
        floorLine.addTiles(excessTiles);
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
        excessTiles.add(Color.BLUE);
        excessTiles.add(Color.BLUE);
        excessTiles.add(Color.BLUE);
        excessTiles.add(Color.BLUE);
        floorLine.addTiles(excessTiles);
        assertTrue(floorLine.getCopyTiles().size() <= 7);
        assertTrue(floorLine.getScore()>= -14);
    }
    @Test
    public void testClearTiles(){
        floorLine.clearTiles();
        assertEquals(floorLine.getCopyTiles().size(),0);
    }
}