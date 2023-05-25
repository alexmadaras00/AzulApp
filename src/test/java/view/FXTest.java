package view;

import org.junit.jupiter.api.BeforeAll;

import javafx.application.Application;
import javafx.stage.Stage;

public class FXTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        return;
    }

    @BeforeAll
    public static void startup() {
        new Thread(() -> {
            launch();
        }).start();
    }
}
