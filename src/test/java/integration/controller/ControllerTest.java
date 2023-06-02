package integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import controller.Controller;
import controller.Mediator;
import dataobjects.*;
import model.TileColor;
import model.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dataobjects.executors.Executor;
import model.Model;
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
        public void notify(DataObject message) {
            notifications.add(message);
        }

        @Override
        public void send(DataObject message) {
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

        public DataObject doSomething() {
            didSomethingCounter++;
            return MockDataObject.UPDATE;
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
        public PlayerData addPlayer(PlayerData playerData) {
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
        public MoveUpdate performMovePatternLineFloorLine(List<Tile> tiles, int patternLineRow, TileColor tileColor) {
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

        @Override
        public boolean isValidMovePatternLineFloorLine(List<Tile> tiles, int patternLineRow, TileColor tileColor) {
            return false;
        }
    }

    private static class MockExecutor implements Executor {

        public MockExecutor(DataObject message) {}

        @Override
        public DataObject execute(Executable model) {
            MockModel newModel = (ControllerTest.MockModel) model;
            return newModel.doSomething();
        }

    }

    private static class MockExecutorFactory implements ExecutorFactory {

        @Override
        public Executor createExecutor(DataObject message) {
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
        messager.send(MockDataObject.MOVE);
        assertEquals(1, model.didSomethingCounter);
        assertEquals(1, messager.notifications.size());
        assertEquals(MockDataObject.UPDATE, messager.notifications.get(0));
    }
}
