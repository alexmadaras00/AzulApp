package main;

import controller.Controller;
import controller.Mediator;
import dataobjects.DataObjectExecutorFactory;
import dataobjects.ExecutorFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;
import model.Model;
import view.GUI;
import view.Messager;
import view.View;

public class AzulApp extends Application {

    public static void main(String[] args) {
        launch();
        
    }

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Game();
        ExecutorFactory executorFactory = new DataObjectExecutorFactory();
        Mediator mediator = new Controller(executorFactory);
        Messager messager = new View(executorFactory);
        GUI gui = new GUI();
        mediator.connectModel(model);
        mediator.connectMessager(messager);
        messager.connectMediator(mediator);
        messager.connectUI(gui);
        gui.start(stage);
    }

}
