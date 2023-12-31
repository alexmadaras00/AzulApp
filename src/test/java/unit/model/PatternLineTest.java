package unit.model;

import model.PatternLine;
import model.Tile;
import model.TileColor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatternLineTest {
    static PatternLine patternLine;
    static List<Tile> redTiles;
    static List<Tile> redTile;
    static List<Tile> blueTiles;
    static List<Integer> rows;

    @BeforeEach
    public void setUp() {
        patternLine = new PatternLine();
        redTile = new ArrayList<>();
        redTile.add(TileColor.RED);
        redTiles = new ArrayList<>();
        redTiles.add(TileColor.RED);
        redTiles.add(TileColor.RED);
        redTiles.add(TileColor.RED);
        redTiles.add(TileColor.RED);
        blueTiles = new ArrayList<>();
        blueTiles.add(TileColor.BLUE);
        blueTiles.add(TileColor.BLUE);
        rows = new ArrayList<>();
        rows.add(1);
        rows.add(3);
    }

    @Test
    void ShouldBeImmutable() {
        PatternLine patternLine = new PatternLine();
        List<List<Tile>> copyTable = patternLine.getCopyTable();
        assertThrows(UnsupportedOperationException.class, () -> copyTable.set(4, redTiles));
    }

    @Test
    public void testReturnExcessTiles() {
        List<Tile> excessTiles = patternLine.addTiles(2, redTile);
        assertEquals(excessTiles.size(), 0);
        excessTiles = patternLine.addTiles(2, redTiles);
        assertEquals(excessTiles.size(), 2);
    }

    @Test
    public void testCannotAddTilesDifferentColor() {
        patternLine.addTiles(4, redTiles);
        assertTrue(patternLine.canAddTile(4, TileColor.RED));
        assertFalse(patternLine.canAddTile(4, TileColor.BLUE));
    }

    @Test
    public void testCannotAddTilesIfFull() {
        patternLine.addTiles(3, redTile);
        assertTrue(patternLine.canAddTile(3, TileColor.RED));
        patternLine.addTiles(3, redTiles);
        assertFalse(patternLine.canAddTile(3, TileColor.RED));
        patternLine.clearTiles(3);
        assertTrue(patternLine.canAddTile(3, TileColor.RED));
    }

    @Test
    public void testClearTiles() {
        patternLine.addTiles(3, redTiles);
        patternLine.addTiles(1, blueTiles);
        List<Tile> clearedTiles = patternLine.clearTiles(3);
        assertEquals(clearedTiles.size(), 4);
        clearedTiles = patternLine.clearTiles(1);
        assertEquals(clearedTiles.size(), 2);
    }

    @Test
    public void testCompletedRows() {
        patternLine.addTiles(3, redTiles);
        patternLine.addTiles(1, blueTiles);
        patternLine.addTiles(2, blueTiles);
        assertEquals(rows, patternLine.completedRows());
    }
}