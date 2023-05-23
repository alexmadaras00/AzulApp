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
import view.Messager;
import view.UI;
import view.View;

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
        public void addTileFactory(int factory, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTileFactory'");
        }

        @Override
        public void removeTileFactory(int factory, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeTileFactory'");
        }

        @Override
        public void clearFactory(int factory) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearFactory'");
        }

        @Override
        public void addTileMiddle(Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTileMiddle'");
        }

        @Override
        public void removeTileMiddle(Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeTileMiddle'");
        }

        @Override
        public void clearMiddle() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearMiddle'");
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
        public void addTileWall(int PlayerID, int row, int column, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTileWall'");
        }

        @Override
        public void removeTileWall(int PlayerID, int row, int column, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeTileWall'");
        }

        @Override
        public void clearWall(int PlayerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearWall'");
        }

        @Override
        public void addTilePattern(int PlayerID, int row, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTilePattern'");
        }

        @Override
        public void removeTilePattern(int PlayerID, int row, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeTilePattern'");
        }

        @Override
        public void clearPatternLine(int PlayerID, int row) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearPatternLine'");
        }

        @Override
        public void clearPattern(int PlayerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearPattern'");
        }

        @Override
        public void addTileFloorLine(int PlayerID, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTileFloorLine'");
        }

        @Override
        public void removeTileFloorLine(int PlayerID, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeTileFloorLine'");
        }

        @Override
        public void clearFloorLine(int PlayerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearFloorLine'");
        }

        @Override
        public void setPlayerName(int PlayerID, String name) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setPlayerName'");
        }

        @Override
        public void setScore(int PlayerID, int Score) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setScore'");
        }

        @Override
        public void clearPlayer(int PlayerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearPlayer'");
        }

        @Override
        public void loadGameState(GameState gameState) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'saveGameState'");
        }

        @Override
        public void clearGameState() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearGameState'");
        }

        @Override
        public void setActivePlayerView(int playerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setActivePlayerView'");
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
