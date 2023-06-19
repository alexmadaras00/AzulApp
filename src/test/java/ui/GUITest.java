package ui;

import controller.Controller;
import controller.ControllerImpl;
import controller.GameProxy;
import javafx.stage.Stage;
import model.Game;
import model.Model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.testfx.framework.junit5.ApplicationTest;

import view.GUI;
import view.ViewUpdateListener;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class GUITest extends ApplicationTest {
    private GUI view;
    private Model game;
    private Controller controller;
    private ViewUpdateListener listener;
    private GameProxy modelProxy;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.view = new GUI();
        this.game = new Game();
        game.addPlayer("Lian");
        game.addPlayer("Stan");
        this.modelProxy = new GameProxy();
        modelProxy.setGame(game);
        URL urlHub = getClass().getResource("/view/HubPage.fxml");
        URL urlGame = getClass().getResource("/view/GamePage.fxml");
        this.controller = new ControllerImpl();
        view.setModel(modelProxy);
        view.setController(controller);
        view.setup(stage, urlHub, urlGame);
        view.showHub();
    }

    @Test
    public void testSetController() {
        view.setController(controller);
        assertEquals(controller, view.getController());
    }

    @Test
    public void testShowHub() {
        assertNotNull(view.getHubPageView());
        assertNotNull(view.getHubPageController());
        assertNotNull(view.getStage());
        assertEquals(view.getCurrentPage(), view.getHubPageController());
    }

    @Test
    public void testShowGame() {
        clickOn("#playerName1").write("Stan");
        clickOn("#joinButton1");
        clickOn("#playerName2").write("LexxFlexx");
        clickOn("#joinButton2");
        clickOn("#startButton");
        assertEquals(view.getCurrentPage(), view.getGamePageController());
    }
}
