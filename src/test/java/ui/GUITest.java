package ui;

import controller.Controller;
import controller.ControllerImpl;
import controller.GameProxy;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Game;
import model.Model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import view.GUI;
import view.GamePage;
import view.HubPage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class GUITest extends ApplicationTest {
    private GUI view;
    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        this.view = new GUI();
        URL urlHub = getClass().getResource("/view/HubPage.fxml");
        URL urlGame = getClass().getResource("/view/GamePage.fxml");
        this.controller = new ControllerImpl();
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
    public void testJoinGame() {
        clickOn("#playerName1").write("Stan");
        clickOn("#joinButton1");
        FxAssert.verifyThat("#joinButton1", (Button b) -> b.isDisabled());
        clickOn("#joinButton2");
        FxAssert.verifyThat("#joinButton2", (Button b) -> !b.isDisabled());
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

    @Test
    public void testDoGame() {
        clickOn("#playerName3").write("Stan");
        clickOn("#joinButton3");
        clickOn("#playerName4").write("LexxFlexx");
        clickOn("#joinButton4");
        clickOn("#startButton");
        assertEquals(view.getCurrentPage(), view.getGamePageController());
        clickOn("#buttonF1T1");
        clickOn("#player1PL5");
        FxAssert.verifyThat("#player1PL5", (GridPane g) -> g.getChildren().size() > 0);
    }

    @Test
    public void testToastHubPage() {
        String message = "Belgium is Netherlands!";
        final PrintStream standardOut = System.out;
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertInstanceOf(HubPage.class, view.getCurrentPage());
        view.toast(message);
        assertEquals(message, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testToastGamePage() {
        String message = "Belgium is Netherlands!";
        clickOn("#playerName3").write("Stan");
        clickOn("#joinButton3");
        clickOn("#playerName4").write("LexxFlexx");
        clickOn("#joinButton4");
        clickOn("#startButton");
        final PrintStream standardOut = System.out;
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertInstanceOf(GamePage.class, view.getCurrentPage());
        view.toast(message);
        assertEquals(message, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testUpdate() {
        assertInstanceOf(HubPage.class, view.getCurrentPage());
        view.update();
    }


}
