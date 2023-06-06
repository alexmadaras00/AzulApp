package unit.view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import javafxHelpers.JavaFXApplicationTest;
import model.TileColor;
import view.DisplayPatternLine;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class DisplayPatternLineTest extends JavaFXApplicationTest{
    private DisplayPatternLine displayPatternLine;
    

    @BeforeEach
    public void setUp() {
        displayPatternLine = new DisplayPatternLine();
    }

    @Test
    public void testConstructorPatternLine() {
        assertDoesNotThrow(() -> {
            displayPatternLine = new DisplayPatternLine();
        });
    }

    @Test
    public void testAddTilePatternLine() {
        assertDoesNotThrow(() -> {
            displayPatternLine.addTile(0, TileColor.RED);
        });
    }

    @Test
    public void testRemoveTilePatternLine() {
        displayPatternLine.addTile(0, TileColor.RED);
        assertDoesNotThrow(() -> {
            displayPatternLine.removeTile(0);
        });

    }

    @Test
    public void testClearPatternLine() {
        displayPatternLine.addTile(0, TileColor.RED);
        assertDoesNotThrow(() -> {
            displayPatternLine.clear();
        });
    }
}
