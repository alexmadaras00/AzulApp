package main;

import controller.IController;
import model.Game;
import model.Model;
import view.IView;

public class AzulApp {
    Model model;
    IController IController;
    IView IView;
    public static void main(String[] args) {
        Game azul = new Game();
    }
}
