package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.Mediator;
import dataobjects.DataObject;
import dataobjects.Executable;
import dataobjects.ExecutorFactory;
import dataobjects.GameState;
import dataobjects.executors.Executor;
import model.Model;
import model.Tile;

public class ViewTest {
    private static enum MockDataObject implements DataObject {
        MOVE,
        UPDATE;
    }

    private static class MockUI implements UI {
        public int didSomethingCounter;

        public MockUI() {
            didSomethingCounter = 0;
        }

        public void doSomething() {
            didSomethingCounter++;
        }

        @Override
        public void connectMessager(Messager messager) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'connectMessager'");
        }

        @Override
        public void resetGameState() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'resetGameState'");
        }

        @Override
        public void clearAll() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearAll'");
        }

        @Override
        public void commit() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'commit'");
        }

        @Override
        public void addFactory(int factoryID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addFactory'");
        }

        @Override
        public void addTileFactory(int factoryID, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTileFactory'");
        }

        @Override
        public void removeTilesFactory(int factoryID, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeTilesFactory'");
        }

        @Override
        public void clearFactory(int factoryID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearFactory'");
        }

        @Override
        public void addTileMiddle(Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTileMiddle'");
        }

        @Override
        public void removeTilesMiddle(Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeTilesMiddle'");
        }

        @Override
        public void clearMiddle() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearMiddle'");
        }

        @Override
        public void addPlayer(int playerID, String name) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addPlayer'");
        }

        @Override
        public void setPlayerName(int playerID, String name) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setPlayerName'");
        }

        @Override
        public void setScore(int playerID, int score) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setScore'");
        }

        @Override
        public void setActivePlayerView(int playerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setActivePlayerView'");
        }

        @Override
        public void clearPlayer(int playerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearPlayer'");
        }

        @Override
        public void addTileWall(int PlayerID, int row, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTileWall'");
        }

        @Override
        public void removeTilesWall(int playerID, int row, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeTilesWall'");
        }

        @Override
        public void clearWall(int playerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearWall'");
        }

        @Override
        public void addTilePattern(int playerID, int row, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTilePattern'");
        }

        @Override
        public void removeTilePattern(int playerID, int row, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeTilePattern'");
        }

        @Override
        public void clearPatternLine(int playerID, int row) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearPatternLine'");
        }

        @Override
        public void clearPattern(int playerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearPattern'");
        }

        @Override
        public void addTileFloorLine(int playerID, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTileFloorLine'");
        }

        @Override
        public void removeTilesFloorLine(int playerID, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeTilesFloorLine'");
        }

        @Override
        public void clearFloorLine(int playerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearFloorLine'");
        }

    }

    private static class MockController implements Mediator {
        public List<DataObject> notifications = new ArrayList<>();
        private Messager messager;

        @Override
        public void notify(DataObject message) {
            notifications.add(message);
            messager.notify(MockDataObject.UPDATE);
        }

        @Override
        public void connectMessager(Messager messager) {
            this.messager = messager;
        }

        @Override
        public void connectModel(Model model) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'connectModel'");
        }
    }

    private static class MockExecutor implements Executor {

        public MockExecutor(DataObject message) {
        }

        @Override
        public DataObject execute(Executable ui) {
            MockUI newUserInterface = (ViewTest.MockUI) ui;
            newUserInterface.doSomething();
            return null;
        }

    }

    private static class MockExecutorFactory implements ExecutorFactory {

        @Override
        public Executor createExecutor(DataObject message) {
            return new MockExecutor(message);
        }
    }

    private static Messager view;
    private static MockUI ui;
    private static MockController controller;

    @BeforeAll
    static void setUp() {
        ui = new MockUI();
        view = new View(new MockExecutorFactory());
        controller = new MockController();
        controller.connectMessager(view);
        view.connectMediator(controller);
        view.connectUI(ui);
    }

    @Test
    void testConnectionViewToController() {
        view.send(MockDataObject.MOVE);
        assertEquals(1, controller.notifications.size());
        assertEquals(MockDataObject.MOVE, controller.notifications.get(0));
        assertEquals(1, ui.didSomethingCounter);
    }
}
