package main;

import controller.ControllerImpl;
import controller.GameProxy;
import javafx.application.Application;
import javafx.stage.Stage;

import model.Game;
import model.Model;
import view.GUI;

public class AzulApp extends Application {
    private static boolean useTwinteamFactory = true;
    private Model model;
    private ControllerImpl controller;
    private GUI view;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        controller = new ControllerImpl(useTwinteamFactory);
        view = new GUI();
        view.setController(controller);
        view.setup(stage, AzulApp.class.getResource("/view/HubPage.fxml"),
                AzulApp.class.getResource("/view/GamePage.fxml"));
        view.showHub();
    }

}
