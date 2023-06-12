package integration.messaging.executors;

import messaging.executors.controller.StartGameExecutor;
import messaging.messages.Message;
import messaging.messages.NotOkStartGame;
import messaging.messages.OkStartGame;
import messaging.messages.StartGame;
import model.Game;
import model.Model;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StartGameExecutorTest {
    static private Model model;
    static private StartGameExecutor startGameExecutor;
    static private Player player1;
    static private Player player2;
    static private Player player3;
    static private Player player4;
    static private Player player5;
    static private Message message;

    @BeforeEach
    public void setUp() {
        model = new Game();
        startGameExecutor = new StartGameExecutor();
        player1 = new Player("StanTheGreat");
        player2 = new Player("Alladin");
        player3 = new Player("Donald");
        player4 = new Player("Putin");
        player5 = new Player("Manuel");
        message = new StartGame();
    }

    @Test
    public void testExecuteCanStartGame() {
        model.addPlayer(player1);
        model.addPlayer(player2);
        startGameExecutor.setMessage(message);
        assertInstanceOf(OkStartGame.class, startGameExecutor.execute(model));
    }

    @Test
    public void testExecuteCannotStartGame() {
        model.addPlayer(player1);
        startGameExecutor.setMessage(message);
        NotOkStartGame badRequestTooFewPlayers = (NotOkStartGame) startGameExecutor.execute(model);
        assertInstanceOf(NotOkStartGame.class, badRequestTooFewPlayers);
        assertEquals(
                "Invalid number of players. The game requires at least 2 and at most 4 players. Please adjust the number of players and try again.",
                badRequestTooFewPlayers.getReason());
    }

    @Test
    public void testSetMessage() {
        assertDoesNotThrow(() -> {
            startGameExecutor.setMessage(message);
        });
    }

}
