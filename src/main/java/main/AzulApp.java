package main;

import controller.Controller;
import controller.Mediator;
import dataobjects.DataObjectExecutorFactory;
import dataobjects.ExecutorFactory;
import model.Game;
import model.Model;
import view.GUI;
import view.Messager;
import view.View;

public class AzulApp {

    public static void main(String[] args) {
        Model model = new Game();
        ExecutorFactory executorFactory = new DataObjectExecutorFactory();
        Mediator mediator = new Controller(executorFactory);
        Messager messager = new View(executorFactory);
        GUI gui = new GUI();
        mediator.connectModel(model);
        mediator.connectMessager(messager);
        messager.connectMediator(mediator);
        messager.connectUI(gui);
    }

    
}
