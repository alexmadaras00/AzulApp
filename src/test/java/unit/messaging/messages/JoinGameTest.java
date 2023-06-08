package unit.messaging.messages;

import messaging.messages.JoinGame;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JoinGameTest {
    static JoinGame joinGame;
    static Player player;

    @BeforeEach
    public void setup() {
        player = new Player("Mihai");
        joinGame = new JoinGame(player.getName());
    }
    @Test
    public void testGetName() {
        assertEquals(player.getName(), joinGame.getPlayerName());
        assertNotEquals("SergeTheBelgian", joinGame.getPlayerName());
    }
}
