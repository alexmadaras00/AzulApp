package integration.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.ArrayList;
import java.util.List;

import messaging.messages.Message;
import messaging.messages.StartGame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.Mediator;
import messaging.dataobjects.DataObject;
import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.executors.ExecutorFactory;
import model.Model;
import model.Tile;
import model.TileColor;
import view.Messager;
import view.UI;
import view.View;

public class ViewTest {

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
            return;
        }

        @Override
        public void startGame() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'startGame'");
        }

        @Override
        public void showToast(String message) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'showToast'");
        }

        @Override
        public void addPlayer(int playerID, String name) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addPlayer'");
        }

        @Override
        public void notAddPlayer(String playerName) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'notAddPlayer'");
        }

        @Override
        public void factorySetup(List<Integer> factoryIDs) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'factorySetup'");
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
        public void setCurrentPlayer(int playerID) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setCurrentPlayer'");
        }

        @Override
        public void addTileWall(int PlayerID, int row, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTileWall'");
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
        public void addTileFloorLine(int playerID, Tile tile) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addTileFloorLine'");
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
        public void notify(Message message) {
            notifications.add(message);
            messager.notify(new StartGame());
        }

        @Override
        public void connectMessager(Messager messager) {
            this.messager = messager;
        }

        @Override
        public void connectModel(Model model) {
            return;
        }
    }

    private static class MockExecutor implements Executor {

        public MockExecutor(Message message) {
        }

        @Override
        public Message execute(Executable ui) {
            MockUI newUserInterface = (ViewTest.MockUI) ui;
            newUserInterface.doSomething();
            return null;
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

    private static Messager view;
    private static MockUI ui;
    private static MockController controller;

    @BeforeAll
    static void setUp() {
        ui = new MockUI();
        View.setExecutorFactory(new MockExecutorFactory());
        view = View.getInstance();
        controller = new MockController();
        controller.connectMessager(view);
        view.connectMediator(controller);
        view.connectUI(ui);
    }

    @Test
    void testConnectionViewToController() {
        view.send(new StartGame());
        assertEquals(1, controller.notifications.size());
        assertInstanceOf(StartGame.class, controller.notifications.get(0));
        assertEquals(1, ui.didSomethingCounter);
    }
}
