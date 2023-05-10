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
    static List<Integer> scores;
    @BeforeEach
    public void setUp() {
        floorLine = new FloorLine();
        excessTiles = new ArrayList<>();
        scores = new ArrayList<>();
        excessTiles.add(Color.RED);
        excessTiles.add(Color.RED);
        excessTiles.add(Color.RED);
        excessTiles.add(Color.RED);
        scores.add(-1);
        scores.add(-1);
        scores.add(-2);
        scores.add(-2);
        scores.add(-2);
        scores.add(-3);
        scores.add(-3);
    }
    @Test
    public void testGetAllTiles(){
        floorLine.addTiles(excessTiles);
        List<Tile> theTiles = floorLine.getCopyTiles();
        assertEquals(excessTiles, theTiles);
        assertTrue(floorLine.getCopyTiles().size() <= 7);
    }

    @Test
    public void testReachMax(){
        floorLine.addTiles(excessTiles);
        assertTrue(floorLine.getCopyTiles().size() <= 7);
    }
    @Test
    public void testScore(){
        assertTrue(floorLine.getScore()>= -14);
    }
    @Test
    public void testClearTiles(){
        floorLine.clearTiles();
        assertEquals(floorLine.getCopyTiles().size(),0);
    }
}