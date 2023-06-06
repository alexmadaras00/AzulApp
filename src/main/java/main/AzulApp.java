package main;

import controller.Controller;
import controller.Mediator;
import dataobjects.DataObjectExecutorFactory;
import dataobjects.ExecutorFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import view.DisplayWall;
import view.GUI;
import view.Messager;
import view.View;

import java.util.ArrayList;
import java.util.List;

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
        gui.addTileFloorLine(0, TileColor.BLACK);
        gui.addTileFloorLine(0, TileColor.BLACK);
        gui.addTileFloorLine(0, TileColor.BLACK);
        gui.addTilePattern(0, 0, TileColor.CYAN);
        gui.addTilePattern(0, 0, TileColor.CYAN);
        gui.addTilePattern(0, 1, TileColor.CYAN);
        gui.addTilePattern(0, 1, TileColor.CYAN);
        gui.addTilePattern(0, 2, TileColor.CYAN);
        gui.addTilePattern(0, 2, TileColor.CYAN);
        gui.addTilePattern(0, 3, TileColor.CYAN);
        gui.addTilePattern(0, 3, TileColor.CYAN);
        gui.addTilePattern(0, 4, TileColor.CYAN);
        gui.addTilePattern(0, 4, TileColor.CYAN);
        gui.addTileWall(0, 0, TileColor.CYAN);
        gui.addTileWall(0, 0, TileColor.CYAN);
        gui.addTileWall(0, 1, TileColor.CYAN);
        gui.addTileWall(0, 1, TileColor.CYAN);
        gui.addTileWall(0, 2, TileColor.CYAN);
        gui.addTileWall(0, 2, TileColor.CYAN);
        gui.addTileWall(0, 3, TileColor.CYAN);
        gui.addTileWall(0, 3, TileColor.CYAN);
        gui.addTileWall(0, 4, TileColor.CYAN);
        gui.addTileWall(0, 4, TileColor.CYAN);
        gui.addPlayer(1, "Mike");
        gui.addTileFloorLine(1, TileColor.BLACK);
        gui.addTileFloorLine(1, TileColor.BLACK);
        gui.addTileFloorLine(1, TileColor.BLACK);
        gui.addTilePattern(1, 0, TileColor.CYAN);
        gui.addTilePattern(1, 0, TileColor.CYAN);
        gui.addTilePattern(1, 1, TileColor.CYAN);
        gui.addTilePattern(1, 1, TileColor.CYAN);
        gui.addTilePattern(1, 2, TileColor.CYAN);
        gui.addTilePattern(1, 2, TileColor.CYAN);
        gui.addTilePattern(1, 3, TileColor.CYAN);
        gui.addTilePattern(1, 3, TileColor.CYAN);
        gui.addTilePattern(1, 4, TileColor.CYAN);
        gui.addTilePattern(1, 4, TileColor.CYAN);
        gui.addTileWall(1, 0, TileColor.CYAN);
        gui.addTileWall(1, 0, TileColor.CYAN);
        gui.addTileWall(1, 1, TileColor.CYAN);
        gui.addTileWall(1, 1, TileColor.CYAN);
        gui.addTileWall(1, 2, TileColor.CYAN);
        gui.addTileWall(1, 2, TileColor.CYAN);
        gui.addTileWall(1, 3, TileColor.CYAN);
        gui.addTileWall(1, 3, TileColor.CYAN);
        gui.addTileWall(1, 4, TileColor.CYAN);
        gui.addTileWall(1, 4, TileColor.CYAN);
        // gui.addTileWall(0, 0, null);
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
