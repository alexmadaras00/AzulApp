package main;

import controller.ControllerImpl;
import javafx.application.Application;
import javafx.stage.Stage;

import model.Game;
import model.GameProxy;
import model.Model;
import view.GUI;

public class AzulApp extends Application {
    private Model model;
    private ControllerImpl controllerImpl;
    private GUI view;

    public static void main(String[] args) {
        System.out.println("YES");
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.model = new Game();
        this.controllerImpl = new ControllerImpl();
        this.view = new GUI();

        controllerImpl.setModel(model);
        view.setController(controllerImpl);
        GameProxy proxy = new GameProxy();
        proxy.setProxy(model);
        view.setModel(proxy);
        view.setup(stage, AzulApp.class.getResource("/view/HubPage.fxml"),
                AzulApp.class.getResource("/view/GamePage.fxml"));
        view.showHub();

    }

}
