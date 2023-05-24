package view;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DisplayGameStateTest {
    private DisplayGameState gameState;

    @BeforeEach
    public void setUp() {
        gameState = new DisplayGameState();
    }

    @Test
    public void testEmptyOutputNoErrors() {
        assertDoesNotThrow(() -> gameState.toString(), "Tee print does not work correctly");
    }
}
