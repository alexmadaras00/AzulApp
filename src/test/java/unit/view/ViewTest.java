package unit.view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import javafxHelpers.JavaFXApplicationTest;
import model.Game;
import view.View;

public class ViewTest extends JavaFXApplicationTest {
    private View view;

    @BeforeEach
    public void setup() {
        view = new View();
    }

    @Test
    public void testNoThrows() {
        assertDoesNotThrow(() -> {
            view.setModel(new Game());
            view.setController(new Controller());
        });
    }
}
