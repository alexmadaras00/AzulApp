package main;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import model.Game;
import model.Model;
import view.View;

public class AzulApp extends Application {
    private static Model model = null;
    private static Controller controller = null;
    private static View view = null;

    public static View getView() {
        return view;
    }

    public static void setView(View view) {
        AzulApp.view = view;
    }

    public static Model getModel() {
        return model;
    }

    public static void setModel(Model model) {
        AzulApp.model = model;
    }

    public static Controller getController() {
        return controller;
    }

    public static void setController(Controller controller) {
        AzulApp.controller = controller;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        setModel(new Game());
        setController(new Controller());
        setView(new View());
        view.setStage(stage);

        FXMLLoader loaderHub = new FXMLLoader(AzulApp.class.getResource("/view/HubPage.fxml"));
        FXMLLoader loaderGame = new FXMLLoader(AzulApp.class.getResource("/view/GamePage.fxml"));
        view.setHubPageController(loaderHub.getController());
        view.setGamePageController(loaderGame.getController());
        view.setHubPageView(loaderHub.load());
        view.setGamePageView(loaderGame.load());
        view.showHub();
    }

}
