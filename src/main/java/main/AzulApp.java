package main;

import controller.Controller;
import controller.Mediator;
import dataobjects.DataObjectExecutorFactory;
import dataobjects.ExecutorFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;
import model.Model;
import model.TileColor;
import model.Wall;
import view.DisplayWall;
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
        gui.setWallPattern(Wall.wallPattern());
        gui.addFactory(0);
        gui.addFactory(1);

        gui.addPlayer(0, "Bob");
        gui.addTileMiddle(TileColor.RED);
        gui.addTileMiddle(TileColor.RED);
        gui.addTileMiddle(TileColor.RED);
        gui.addTileFactory(0, TileColor.BLUE);
        gui.addTileFactory(0, TileColor.BLUE);
        gui.addTileFactory(0, TileColor.BLUE);
        gui.addTileFactory(1, TileColor.YELLOW);
        gui.addTileFactory(1, TileColor.YELLOW);
    }

}
