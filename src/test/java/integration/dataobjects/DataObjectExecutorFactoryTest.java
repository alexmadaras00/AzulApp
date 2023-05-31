package integration.dataobjects;

import dataobjects.*;
import dataobjects.executors.Executor;
import model.Color;
import model.Model;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataObjectExecutorFactoryTest {
    private static enum MockDataObject implements DataObject {
        MOVE, UPDATE;
    }

    private static class MockModel implements Model {
        public int didSomethingElseCounter = 0;
        public int didSomethingCounter = 0;

        // implement each method from model with counter of execution, returns UPDATE
        public DataObject doSomething() {
            didSomethingCounter++;
            return MockDataObject.UPDATE;
        }

        public DataObject doSomethingElse() {
            didSomethingElseCounter++;
            return MockDataObject.UPDATE;
        }

        @Override
        public GameState startGame() {
            return new GameState();
        }

        @Override
        public RoundUpdate startRound() {
            return new RoundUpdate();
        }

        @Override
        public RoundUpdate endRound() {
            return new RoundUpdate();
        }

        @Override
        public GameState terminateGame() {
            return new GameState();
        }

        @Override
        public GameState endGame() {
            return new GameState();
        }

        @Override
        public boolean isCurrentPlayer(PlayerData player) {
            return false;
        }

        @Override
        public PlayerData addPlayer(PlayerData playerData) {
            return new PlayerData();
        }

        @Override
        public boolean isValidStartGame() {
            return false;
        }

        @Override
        public MoveUpdate performMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, Color color) {
            return new MoveUpdate();
        }

        @Override
        public MoveUpdate performMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, Color color) {
            return new MoveUpdate();
        }

        @Override
        public MoveUpdate performMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex) {
            return new MoveUpdate();
        }

        @Override
        public MoveUpdate performMoveMiddleFloorLine(List<Tile> tiles) {
            return new MoveUpdate();
        }

        @Override
        public MoveUpdate performMovePatternLineFloorLine(List<Tile> tiles, int patternLineRow, Color color) {
            return new MoveUpdate();
        }

        @Override
        public boolean isValidMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, Color color) {
            return false;
        }

        @Override
        public boolean isValidMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex) {
            return false;
        }

        @Override
        public boolean isValidMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, Color color) {
            return false;
        }

        @Override
        public boolean isValidMoveMiddleFloorLine(List<Tile> tiles) {
            return false;
        }

        @Override
        public boolean isValidMovePatternLineFloorLine(List<Tile> tiles, int patternLineRow, Color color) {
            return false;
        }
    }

    private static ExecutorFactory factory;
    private static MockModel model;

    @BeforeEach
    public static void setUp() {
        model = new MockModel();
        factory = new DataObjectExecutorFactory();
    }

    @Disabled
    public void testMoveMessage() {
        // TODO EXAMPLE test per datatype
        Executor executor = factory.createExecutor(new Move());
        DataObject dataobject = executor.execute(model);
        assertEquals(MockDataObject.UPDATE, dataobject);
        assertEquals(1, model.didSomethingCounter);
        assertEquals(0, model.didSomethingElseCounter);
    }
}
