package main;

import controller.ControllerImpl;
import javafx.application.Application;
import javafx.stage.Stage;

import model.Game;
import model.GameProxy;
import model.Model;
import view.View;

public class AzulApp extends Application {
    private Model model;
    private ControllerImpl controllerImpl;
    private View view;

    public static void main(String[] args) {
        System.out.println("YES");
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.model = new Game();
        this.model.useTwinteamFactory();
        this.controllerImpl = new ControllerImpl();
        this.view = new View();

        controllerImpl.setModel(model);
        controllerImpl.setView(view);
        view.setController(controllerImpl);
        GameProxy proxy = new GameProxy();
        proxy.setProxy(model);
        view.setModel(proxy);
        view.setup(stage, AzulApp.class.getResource("/view/HubPage.fxml"),
                AzulApp.class.getResource("/view/GamePage.fxml"));
        view.showHub();

    }

}
