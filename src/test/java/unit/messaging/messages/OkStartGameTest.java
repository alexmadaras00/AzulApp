package unit.messaging.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import messaging.dataobjects.GameState;
import messaging.messages.OkStartGame;

public class OkStartGameTest {
    private OkStartGame response;
    private String requestId = "1";
    private GameState gameState = new GameState();


    @BeforeEach
    public void setup() {
        response = new OkStartGame(requestId);
    }

    @Test
    public void testRequestId() {
        assertEquals(requestId, response.getRequestId());
    }

    @Test
    public void testSetData() {
        response.setGameState(gameState);
        assertEquals(gameState, response.getGameState());
    }
}
