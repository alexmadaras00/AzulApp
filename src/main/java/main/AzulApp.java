package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.Controller;
import controller.Mediator;
import javafx.application.Application;
import javafx.stage.Stage;
import messaging.executors.ConcreteExecutorFactory;
import messaging.executors.ExecutorFactory;
import model.Game;
import model.Model;
import model.TileColor;
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
        gui.factorySetup(List.of(1, 2, 3, 4, 5));
        gui.addPlayer(15, "Henk de ST");
        gui.addPlayer(14, "Bob the buoder");
        gui.setTilesFactory(2, List.of(TileColor.BLUE, TileColor.BLUE, TileColor.RED, TileColor.YELLOW));
        gui.setTilesFactory(4, List.of(TileColor.BLUE, TileColor.CYAN, TileColor.RED, TileColor.BLACK));
        gui.startGame();
        gui.clearFactory(2);
    }

}
