package unit.model;

import model.GamePhase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class GamePhaseTest {

    @Test
    public void testValueComparison() {
        GamePhase initialized = GamePhase.INITIALIZED;
        GamePhase preparingRound = GamePhase.PREPARING_ROUND;
        GamePhase factoryOffer = GamePhase.FACTORY_OFFER;
        GamePhase finished = GamePhase.FINISHED;
        GamePhase terminated = GamePhase.TERMINATED;
        GamePhase wallTilling = GamePhase.WALL_TILLING;

        // Assert equality
        assertEquals(GamePhase.INITIALIZED, initialized);
        assertEquals(GamePhase.PREPARING_ROUND, preparingRound);
        assertEquals(GamePhase.FACTORY_OFFER, factoryOffer);
        assertEquals(GamePhase.FINISHED, finished);
        assertEquals(GamePhase.TERMINATED, terminated);
        assertEquals(GamePhase.WALL_TILLING,wallTilling);

        // Assert inequality
        assertNotEquals(GamePhase.INITIALIZED, terminated);
        assertNotEquals(GamePhase.FACTORY_OFFER, initialized);
        assertNotEquals(GamePhase.PREPARING_ROUND, terminated);
        assertNotEquals(GamePhase.FINISHED, preparingRound);
        assertNotEquals(GamePhase.WALL_TILLING,initialized);
        assertNotEquals(GamePhase.TERMINATED, factoryOffer);
        assertNotEquals(GamePhase.PREPARING_ROUND, finished);
    }
}
