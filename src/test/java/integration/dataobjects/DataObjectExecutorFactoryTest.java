package integration.dataobjects;

import model.Player;
import model.TileColor;
import model.Model;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import messaging.*;
import messaging.dataobjects.DataObject;
import messaging.dataobjects.GameState;
import messaging.dataobjects.Move;
import messaging.dataobjects.MoveUpdate;
import messaging.dataobjects.PlayerData;
import messaging.dataobjects.RoundUpdate;
import messaging.executors.DataObjectExecutorFactory;
import messaging.executors.Executor;
import messaging.executors.ExecutorFactory;

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
        public PlayerData addPlayer(Player playerData) {
            return new PlayerData();
        }

        @Override
        public boolean isValidStartGame() {
            return false;
        }

        @Override
        public DataObject performMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, TileColor tileColor) {
            return new MoveUpdate();
        }

        @Override
        public MoveUpdate performMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor) {
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
