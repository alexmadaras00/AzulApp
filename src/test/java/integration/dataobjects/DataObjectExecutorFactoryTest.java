package integration.dataobjects;

import dataobjects.*;
import dataobjects.executors.Executor;
import model.Player;
import model.TileColor;
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
        public GameState terminateGame() {
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
        public boolean isValidMoveFactoryFloorLine(int factoryIndex, TileColor tileColor) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isValidMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isValidMoveMiddleFloorLine(TileColor tileColor) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isValidMoveMiddlePatternLine(int patternLineRow, TileColor tileColor) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public DataObject performMoveFactoryFloorLine(int factoryIndex, TileColor tileColor) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public DataObject performMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public DataObject performMoveMiddleFloorLine(TileColor tileColor) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public DataObject performMoveMiddlePatternLine(int patternLineRow, TileColor tileColor) {
            // TODO Auto-generated method stub
            return null;
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
