package integration.messaging.executors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import messaging.dataobjects.Action;
import messaging.dataobjects.Location;
import messaging.dataobjects.LocationType;
import messaging.dataobjects.PlayerData;
import messaging.executors.controller.PlayerMoveExecutor;
import messaging.messages.Message;
import messaging.messages.NotOkPlayerMove;
import messaging.messages.OkPlayerMove;
import messaging.messages.PlayerMove;
import model.Game;
import model.Model;
import model.Player;
import model.TileColor;

public class PlayerMoveExecutorTest {
    private Model model;
    private PlayerMoveExecutor executor;
    private Message message;
    private Action action;
    private Player player1;
    private Player player2;
    private PlayerData playerData;
    private Location from;
    private Location to;
    private TileColor tileColor;

    @BeforeEach
    public void setup() {
        model = new Game();
        executor = new PlayerMoveExecutor();
        player1 = new Player("Mika");
        player2 = new Player("Ghost");
        model.addPlayer(player1);
        model.addPlayer(player2);
        model.startGame();
        model.startRound();
        if (!(model.getTurnOrder().get(0) == player1)) {
            player1 = player2;
            player2 = model.getTurnOrder().get(0);
        }
        playerData = new PlayerData();
        playerData.setIdentifier(player1.getIdentifier());
        playerData.setName(player1.getName());
        message = new PlayerMove(playerData);
        action = new Action();
        tileColor = model.getFactories().get(1).getAllTiles().get(0);
        from = new Location();
        from.setType(LocationType.FACTORY);
        from.setIndex(1);
        to = new Location();
        to.setType(LocationType.PATTERN_LINE);
        to.setIndex(3);
        action.setColor(tileColor);
        action.setFrom(from);
        action.setTo(to);
        ((PlayerMove) message).setAction(action);
    }

    // Executor tests
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
        executor.setMessage(new OkPlayerMove("1"));
        Message response = executor.execute(model);
        assertEquals(null, response);
    }

    @Test
    public void testnoModelNoExecute() {
        executor.setMessage(message);
        Message response = executor.execute(null);
        assertEquals(null, response);
    }

    // PlayerData Tests
    @Test
    public void testNotAPlayerPlayerNullNotOkPlayerMove() {
        message = new PlayerMove(null);
        ((PlayerMove) message).setAction(action);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotAPlayer, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    @Test
    public void testNotAPlayerNameNullNotOkPlayerMove() {
        playerData.setName(null);
        message = new PlayerMove(playerData);
        ((PlayerMove) message).setAction(action);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotAPlayer, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    @Test
    public void testNotAPlayerIdNullNotOkPlayerMove() {
        playerData.setName(null);
        message = new PlayerMove(playerData);
        ((PlayerMove) message).setAction(action);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotAPlayer, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    @Test
    public void testNotAPlayerWrongNameNotOkPlayerMove() {
        playerData.setName("NotInGame");
        message = new PlayerMove(playerData);
        ((PlayerMove) message).setAction(action);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotAPlayer, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    @Test
    public void testNotAPlayerWrongIdNotOkPlayerMove() {
        playerData.setIdentifier(-1);
        message = new PlayerMove(playerData);
        ((PlayerMove) message).setAction(action);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotAPlayer, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    @Test
    public void testNotTheirTurnNotOkPlayerMove() {
        playerData.setName(player2.getName());
        playerData.setIdentifier(player2.getIdentifier());
        message = new PlayerMove(playerData);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotTheirTurn, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    // Action tests

    @Test
    public void testNotAnActionNullNotOkPlayerMove() {
        ((PlayerMove) message).setAction(null);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotAnAction, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    @Test
    public void testNotAnActionColorNullNotOkPlayerMove() {
        action.setColor(null);
        ((PlayerMove) message).setAction(action);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotAnAction, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    @Test
    public void testNotAnActionFromNullNotOkPlayerMove() {
        action.setFrom(null);
        ((PlayerMove) message).setAction(action);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotAnAction, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    @Test
    public void testNotAnActionToNullNotOkPlayerMove() {
        action.setTo(null);
        ((PlayerMove) message).setAction(action);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotAnAction, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    @Test
    public void testActionNotAllowedNotOkPlayerMove() {
        action.setFrom(to);
        ((PlayerMove) message).setAction(action);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonActionNotAllowed, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

    @Test
    public void testActionNotPossibleNotOkPlayerMove() {
        for (TileColor tc : TileColor.values()) {
            if (!model.getFactories().get(1).getAllTiles().contains(tc)) {
                action.setColor(tc);
                break;
            }
        }
        ((PlayerMove) message).setAction(action);
        executor.setMessage(message);
        Message response = executor.execute(model);
        assertEquals(true, response instanceof NotOkPlayerMove);
        assertEquals(PlayerMoveExecutor.reasonNotAnAction, ((NotOkPlayerMove) response).getReason());
        assertEquals(message.getId(), ((NotOkPlayerMove) response).getRequestId());
    }

}
