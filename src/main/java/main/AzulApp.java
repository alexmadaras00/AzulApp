package main;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

import model.Game;
import model.Model;
import view.View;

public class AzulApp extends Application {
    private Model model;
    private Controller controller;
    private View view;

    public static void main(String[] args) {
        System.out.println("YES");
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.model = new Game();
        this.controller = new Controller();
        this.view = new View();

        controller.setModel(model);
        controller.setView(view);
        view.setController(controller);
        view.setModel(model);

        view.setup(stage);
        view.showHub();

    }

}
