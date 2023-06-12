package integration.messaging.executors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import messaging.executors.controller.JoinGameExecutor;
import messaging.messages.BadRequest;
import messaging.messages.JoinGame;
import messaging.messages.Message;
import messaging.messages.OkJoinGame;
import model.Game;
import model.Model;
import model.Player;

public class JoinGameExecutorTest {
    private Model model;
    private JoinGameExecutor executor;
    private Message message;

    @BeforeEach
    public void setup() {
        model = new Game();
        executor = new JoinGameExecutor();
        message = new JoinGame("John");
    }

    @Test
    public void testSetMesage() {
        assertDoesNotThrow(() -> {
            executor.setMessage(message);
        });
    }

    @Test
    public void testNoMessageNoExecute() {
        Message response = executor.execute(model);
        assertEquals(null, response);
    }

    @Test
    public void testWrongMessageNoExecute() {
        executor.setMessage(new OkJoinGame("1"));
        Message response = executor.execute(model);
        assertEquals(null, response);
    }

    @Test
    public void testnoModelNoExecute() {
        executor.setMessage(message);
        Message response = executor.execute(null);
        assertEquals(null, response);
    }

    @Test
    public void testMessageNoNameBadRequest() {
        message = new JoinGame(null);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof BadRequest);
        assertEquals(JoinGameExecutor.reasonNoName, ((BadRequest) response).getReason());
        assertEquals(message.getId(), ((BadRequest) response).getRequestId());

    }

    @Test
    public void testModelFullBadRequest() {
        model.addPlayer(new Player("a"));
        model.addPlayer(new Player("b"));
        model.addPlayer(new Player("c"));
        model.addPlayer(new Player("d"));
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof BadRequest);
        assertEquals(JoinGameExecutor.reasonMaxPlayers, ((BadRequest) response).getReason());
        assertEquals(message.getId(), ((BadRequest) response).getRequestId());
    }

    @Test
    public void testModelAlreadyStartedBadRequest() {
        model.addPlayer(new Player("a"));
        model.addPlayer(new Player("b"));
        model.startGame();
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof BadRequest);
        assertEquals(JoinGameExecutor.reasonGameAlreadyStated, ((BadRequest) response).getReason());
        assertEquals(message.getId(), ((BadRequest) response).getRequestId());
    }
}
