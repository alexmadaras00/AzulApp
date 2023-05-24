package view;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DisplayRowTest {
    private DisplayColumn col;
    private DisplayRow row;

    public static class MockDisplayTile extends DisplayTile {

        public MockDisplayTile() {
            super(null);
        }

        @Override
        public String toString() {
            return "T";
        }
    }

    @BeforeEach
    public void setUp() {
        col = new DisplayColumn();
        row = new DisplayRow(1);
    }

    @Test
    public void MockWorking() {
        DisplayTile tile = new MockDisplayTile();
        assertEquals("T", tile.toString());
    }

    @Test
    public void testEmptyRow() {
        assertEquals("", row.toString());
    }

    @Test
    public void testSingleTileRow() {
        row.addDisplay(new MockDisplayTile());
        assertEquals("T", row.toString());
        List<String> result = row.toStringList();
        assertEquals(1, result.size());
        assertEquals("T", result.get(0));

    }

    @Test
    public void testMultipleTileRow() {
        row.addDisplay(new MockDisplayTile());
        row.addDisplay(new MockDisplayTile());
        row.addDisplay(new MockDisplayTile());
        assertEquals("TTT", row.toString());
        List<String> result = row.toStringList();
        assertEquals(1, result.size());
        assertEquals("TTT", result.get(0));
    }

    @Test
    public void testColumnRow() {
        row = new DisplayRow(2);
        col.addDisplay(new MockDisplayTile());
        col.addDisplay(new MockDisplayTile());
        row.addDisplay(col);
        row.addDisplay(col);
        row.addDisplay(col);
        assertEquals("TTT\nTTT", row.toString());
        List<String> result = row.toStringList();
        assertEquals(2, result.size());
        assertEquals("TTT", result.get(0));
        assertEquals("TTT", result.get(1));
    }
}
