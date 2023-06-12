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
        public void resetGameState() {
            return;
        }

        @Override
        public void clearAll() {
            return;
        }

        @Override
        public void commit() {
            return;
        }

        @Override
        public void addFactory(int factoryID) {
            return;
        }

        @Override
        public void addTileFactory(int factoryID, Tile tile) {
            return;
        }

        @Override
        public void removeTilesFactory(int factoryID, Tile tile) {
            return;
        }

        @Override
        public void clearFactory(int factoryID) {
            return;
        }

        @Override
        public void addTileMiddle(Tile tile) {
            return;
        }

        @Override
        public void removeTilesMiddle(Tile tile) {
            return;
        }

        @Override
        public void clearMiddle() {
            return;
        }

        @Override
        public void addPlayer(int playerID, String name) {
            return;
        }

        @Override
        public void setPlayerName(int playerID, String name) {
            return;
        }

        @Override
        public void setScore(int playerID, int score) {
            return;
        }

        @Override
        public void setActivePlayerView(int playerID) {
            return;
        }

        @Override
        public void clearPlayer(int playerID) {
            return;
        }

        @Override
        public void addTileWall(int PlayerID, int row, Tile tile) {
            return;
        }

        @Override
        public void removeTilesWall(int playerID, int row, Tile tile) {
            return;
        }

        @Override
        public void clearWall(int playerID) {
            return;
        }

        @Override
        public void addTilePattern(int playerID, int row, Tile tile) {
            return;
        }

        @Override
        public void removeTilePattern(int playerID, int row, Tile tile) {
            return;
        }

        @Override
        public void clearPatternLine(int playerID, int row) {
            return;
        }

        @Override
        public void clearPattern(int playerID) {
            return;
        }

        @Override
        public void addTileFloorLine(int playerID, Tile tile) {
            return;
        }

        @Override
        public void removeTilesFloorLine(int playerID, Tile tile) {
            return;
        }

        @Override
        public void clearFloorLine(int playerID) {
            return;
        }

        @Override
        public void setWallPattern(List<List<TileColor>> pttern) {
            return;
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
        view = new View(new MockExecutorFactory());
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
