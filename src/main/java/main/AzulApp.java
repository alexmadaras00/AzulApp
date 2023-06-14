package main;

import controller.ControllerImpl;
import javafx.application.Application;
import javafx.stage.Stage;

import model.Game;
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
        this.controllerImpl = new ControllerImpl();
        this.view = new View();

        controllerImpl.setModel(model);
        controllerImpl.setView(view);
        view.setController(controllerImpl);
        view.setModel(model);

        view.setup(stage);
        view.showHub();

    }

}
