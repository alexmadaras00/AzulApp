package view;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import view.DisplayColumn;
import view.DisplayRow;
import view.DisplayTile;

public class DisplayColumnTest {
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
    public void testEmptyColumn() {
        assertEquals("", col.toString());
    }

    @Test
    public void testSingleTileColumn() {
        col.addDisplay(new MockDisplayTile());
        assertEquals("T", col.toString());
        List<String> result = col.toStringList();
        assertEquals(1, result.size());
        assertEquals("T", result.get(0));

    }

    @Test
    public void testMultipleTileColumn() {
        col.addDisplay(new MockDisplayTile());
        col.addDisplay(new MockDisplayTile());
        col.addDisplay(new MockDisplayTile());
        assertEquals("T\nT\nT", col.toString());
        List<String> result = col.toStringList();
        assertEquals(3, result.size());
        assertEquals("T", result.get(0));
        assertEquals("T", result.get(1));
        assertEquals("T", result.get(2));

    }

    @Test
    public void testRowColumn() {
        row.addDisplay(new MockDisplayTile());
        row.addDisplay(new MockDisplayTile());
        col.addDisplay(row);
        col.addDisplay(row);
        col.addDisplay(row);
        assertEquals("TT\nTT\nTT", col.toString());
        List<String> result = col.toStringList();
        assertEquals(3, result.size());
        assertEquals("TT", result.get(0));
        assertEquals("TT", result.get(1));
        assertEquals("TT", result.get(2));
    }
}
