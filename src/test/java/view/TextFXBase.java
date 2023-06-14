package view;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import main.AzulApp;
import model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.concurrent.TimeoutException;

class TextFXBase extends ApplicationTest {
    Game game;
    GamePage gamePage;
    View view;

    @Before
    public void setUpClass() throws Exception{
        game = new Game();
        ApplicationTest.launch(AzulApp.class);
    }

    @Override
    public void start(Stage stage) throws Exception{
        stage.show();
    }

    @After
    public void afterEachTest() throws TimeoutException{
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    public <T extends Node> T find(final String query){
        return (T) lookup(query).queryAll().iterator().next();
    }

    @Test
    public void test(){

    }
}