import model.Color;
import model.GamePhase;
import model.Middle;
import model.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GamePhaseTest {

    @Test
    public void testValueComparison() {
        GamePhase initialized = GamePhase.Initialized;
        GamePhase preparingRound = GamePhase.PreparingRound;
        GamePhase factoryOffer = GamePhase.FactoryOffer;
        GamePhase finished = GamePhase.Finished;
        GamePhase terminated = GamePhase.Terminated;

        // Assert equality
        assertEquals(GamePhase.Initialized, initialized);
        assertEquals(GamePhase.PreparingRound, preparingRound);
        assertEquals(GamePhase.FactoryOffer, factoryOffer);
        assertEquals(GamePhase.Finished, finished);
        assertEquals(GamePhase.Terminated, terminated);

        // Assert inequality
        assertNotEquals(GamePhase.Initialized, terminated);
        assertNotEquals(GamePhase.FactoryOffer, initialized);
        assertNotEquals(GamePhase.PreparingRound, terminated);
        assertNotEquals(GamePhase.Finished, preparingRound);
        assertNotEquals(GamePhase.Terminated, factoryOffer);
        assertNotEquals(GamePhase.PreparingRound, finished);
    }
}
