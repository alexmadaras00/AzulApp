package unit.view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import javafxHelpers.JavaFXApplicationTest;
import model.TileColor;
import view.DisplayTile;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class DisplayTileTest extends JavaFXApplicationTest{
    private DisplayTile displayTile;

    @Test
    public void testConstructor() {

        assertDoesNotThrow(() -> {
            displayTile = new DisplayTile(TileColor.RED);
        });
    }

}
