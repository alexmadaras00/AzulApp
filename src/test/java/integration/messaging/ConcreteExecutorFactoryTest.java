package integration.messaging;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import messaging.dataobjects.PlayerData;
import messaging.executors.*;
import messaging.executors.controller.*;
import messaging.executors.view.*;
import messaging.messages.*;

public class ConcreteExecutorFactoryTest {
    private static ExecutorFactory factory = new ConcreteExecutorFactory();

    @Test
    public void testSpawnJoinGameExecutor() {
        Executor executor = factory.createExecutor(new JoinGame("John"));
        assertTrue(executor instanceof JoinGameExecutor);
    }

    @Test
    public void testSpawnStartGameExecutor() {
        Executor executor = factory.createExecutor(new StartGame());
        assertTrue(executor instanceof StartGameExecutor);
    }

    @Test
    public void testSpawnPlayerMoveExecutor() {
        Executor executor = factory.createExecutor(new PlayerMove(new PlayerData()));
        assertTrue(executor instanceof PlayerMoveExecutor);
    }

    @Test
    public void testSpawnOkJoinGameExecutor() {
        Executor executor = factory.createExecutor(new OkJoinGame("1"));
        assertTrue(executor instanceof OkJoinGameExecutor);
    }

    @Test
    public void testSpawnOkStartGameExecutor() {
        Executor executor = factory.createExecutor(new OkStartGame("1"));
        assertTrue(executor instanceof OkStartGameExecutor);
    }

    @Test
    public void testSpawnOkPlayerMoveExecutor() {
        Executor executor = factory.createExecutor(new OkPlayerMove("1"));
        assertTrue(executor instanceof OkPlayerMoveExecutor);
    }

    @Test
    public void testSpawnNotOkPlayerMoveExecutor() {
        Executor executor = factory.createExecutor(new NotOkPlayerMove("1"));
        assertTrue(executor instanceof NotOkPlayerMoveExecutor);
    }
    @Test
    public void testSpawnNotOkJoinGameExecutor() {
        Executor executor = factory.createExecutor(new NotOkJoinGame("1"));
        assertTrue(executor instanceof NotOkJoinGameExecutor);
    }
    @Test
    public void testSpawnNotOkStartGameExecutor() {
        Executor executor = factory.createExecutor(new NotOkStartGame("1"));
        assertTrue(executor instanceof NotOkStartGameExecutor);
    }



}
