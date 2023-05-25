package model;

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
        GamePhase initialized = GamePhase.INITIALIZED;
        GamePhase preparingRound = GamePhase.PREPARING_ROUND;
        GamePhase factoryOffer = GamePhase.FACTORY_OFFER;
        GamePhase finished = GamePhase.FINISHED;
        GamePhase terminated = GamePhase.TERMINATED;

        // Assert equality
        assertEquals(GamePhase.INITIALIZED, initialized);
        assertEquals(GamePhase.PREPARING_ROUND, preparingRound);
        assertEquals(GamePhase.FACTORY_OFFER, factoryOffer);
        assertEquals(GamePhase.FINISHED, finished);
        assertEquals(GamePhase.TERMINATED, terminated);

        // Assert inequality
        assertNotEquals(GamePhase.INITIALIZED, terminated);
        assertNotEquals(GamePhase.FACTORY_OFFER, initialized);
        assertNotEquals(GamePhase.PREPARING_ROUND, terminated);
        assertNotEquals(GamePhase.FINISHED, preparingRound);
        assertNotEquals(GamePhase.TERMINATED, factoryOffer);
        assertNotEquals(GamePhase.PREPARING_ROUND, finished);
    }
}
