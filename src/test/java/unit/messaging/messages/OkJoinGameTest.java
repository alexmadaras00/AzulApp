package unit.messaging.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import messaging.dataobjects.PlayerData;
import messaging.messages.OkJoinGame;

public class OkJoinGameTest {
    private OkJoinGame response;
    private String requestId = "1";
    private static String playerName = "john";
    private static int playerId = 4;

    private static PlayerData playerData;

    @BeforeAll
    public static void base() {
        playerData = new PlayerData();
        playerData.setName(playerName);
        playerData.setIdentifier(playerId);
    }

    @BeforeEach
    public void setup() {
        response = new OkJoinGame(requestId);
    }

    @Test
    public void testRequestId() {
        assertEquals(requestId, response.getRequestId());
    }

    @Test
    public void testSetData() {
        response.setPlayerData(playerData);
        assertEquals(playerData, response.getPlayerData());
    }
}
