package main;

import controller.Mediator;
import model.Game;
import model.Model;
import view.Messager;

public class AzulApp {
    Model model;
    Mediator IController;
    Messager IView;
    public static void main(String[] args) {
        Game azul = new Game();
    }
}
