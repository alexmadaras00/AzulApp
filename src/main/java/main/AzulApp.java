package main;

import controller.Controller;
import controller.Mediator;
import javafx.application.Application;
import javafx.stage.Stage;
import messaging.executors.ConcreteExecutorFactory;
import messaging.executors.ExecutorFactory;
import model.Game;
import model.Model;
import view.GUI;
import view.Messager;
import view.UI;
import view.View;

public class AzulApp extends Application {

    public static void main(String[] args) {
        launch();

    }

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Game();
        ExecutorFactory executorFactory = new ConcreteExecutorFactory();
        Mediator mediator = new Controller(executorFactory);
        View.setExecutorFactory(executorFactory);
        Messager messager = View.getInstance();
        GUI gui = new GUI();
        mediator.connectModel(model);
        mediator.connectMessager(messager);
        messager.connectMediator(mediator);
        messager.connectUI(gui);
        gui.start(stage);
    }

}
