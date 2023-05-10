import model.Color;
import model.Factory;
import model.FloorLine;
import model.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FloorLineTest {
    static FloorLine floorLine;
    static List<Tile> excessTiles;
    @BeforeAll
    public static void setUp() {
        floorLine = new FloorLine();
        List<Tile> excessTiles =List.of(Color.RED,Color.RED,Color.RED,Color.RED);
        List<Integer> scores = List.of(-1, -1, -2, -2, -2, -3, -3);
    }
    @Test
    public void testGetAllTiles(){
        floorLine.addTiles(excessTiles);
        assertEquals(excessTiles, floorLine.getCopyTiles());
        assertTrue(floorLine.getCopyTiles().size() <= 7);
    }

    @Test
    public void testReachMax(){
        List<Tile> excessTiles =List.of(Color.BLUE,Color.BLUE,Color.BLUE,Color.BLUE);
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