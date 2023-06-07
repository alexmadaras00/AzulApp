package integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.LinkedList;
import java.util.List;

import controller.Controller;
import controller.Mediator;
import messaging.dataobjects.DataObject;
import messaging.dataobjects.GameState;
import messaging.dataobjects.MoveUpdate;
import messaging.dataobjects.PlayerData;
import messaging.dataobjects.RoundUpdate;
import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.executors.ExecutorFactory;
import messaging.messages.JoinGame;
import messaging.messages.Message;
import messaging.messages.StartGame;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import view.Messager;
import view.UI;

public class ControllerTest {

    private enum MockDataObject implements DataObject {
        MOVE, UPDATE;
    }

    private static class MockMessager implements Messager {
        public List<DataObject> notifications = new LinkedList<DataObject>();
        private Mediator mediator;

        @Override
        public void connectMediator(Mediator mediator) {
            this.mediator = mediator;
        }

        @Override
        public void notify(Message message) {
            notifications.add(message);
        }

        @Override
        public void send(Message message) {
            mediator.notify(message);
        }

        @Override
        public void connectUI(UI userInterface) {
            return;
        }
    }

    private static class MockModel implements Model {
        public int didSomethingCounter;

        public MockModel() {
            didSomethingCounter = 0;
        }

        public Message doSomething() {
            didSomethingCounter++;
            return new StartGame();
        }

        @Override
        public GameState startGame() {
            return null;
        }

        @Override
        public RoundUpdate startRound() {
            return null;
        }

        @Override
        public RoundUpdate endRound() {
            return null;
        }

        @Override
        public GameState terminateGame() {
            return null;
        }

        @Override
        public GameState endGame() {
            return null;
        }

        @Override
        public boolean isCurrentPlayer(PlayerData player) {
            return false;
        }

        @Override
        public PlayerData addPlayer(Player playerData) {
            return null;
        }

        @Override
        public boolean isValidStartGame() {
            return false;
        }

        @Override
        public DataObject performMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, TileColor tileColor) {
            return null;
        }

        @Override
        public MoveUpdate performMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor) {
            return null;
        }

        @Override
        public MoveUpdate performMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex) {
            return null;
        }

        @Override
        public MoveUpdate performMoveMiddleFloorLine(List<Tile> tiles) {
            return null;
        }

        @Override
        public boolean isValidMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, TileColor tileColor) {
            return false;
        }

        @Override
        public boolean isValidMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex) {
            return false;
        }

        @Override
        public boolean isValidMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor) {
            return false;
        }

        @Override
        public boolean isValidMoveMiddleFloorLine(List<Tile> tiles) {
            return false;
        }

    }

    private static class MockExecutor implements Executor {

        public MockExecutor(Message message) {
        }

        @Override
        public Message execute(Executable model) {
            StartGame newMessage = new StartGame();
            return newMessage;
        }

        @Override
        public void setMessage(Message message) {

        }

    }

    private static class MockExecutorFactory implements ExecutorFactory {

        @Override
        public Executor createExecutor(Message message) {
            return new MockExecutor(message);
        }
    }

    private static Mediator controller;
    private static MockModel model;
    private static MockMessager messager;

    @BeforeAll
    public static void setUp() {
        model = new MockModel();
        messager = new MockMessager();
        controller = new Controller(new MockExecutorFactory());
        messager.connectMediator(controller);
        controller.connectMessager(messager);
        controller.connectModel(model);
    }

    @Test
    public void testConnection() {
        assertEquals(0, model.didSomethingCounter);
        assertEquals(0, messager.notifications.size());
        messager.send(new StartGame());
        model.doSomething();
        assertEquals(1, model.didSomethingCounter);
        assertEquals(1, messager.notifications.size());
        assertInstanceOf(StartGame.class, messager.notifications.get(0));
    }
}
