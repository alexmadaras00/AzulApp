package main;

import controller.IController;
import model.Game;
import model.Model;
import view.Messager;

public class AzulApp {
    Model model;
    IController IController;
    Messager IView;
    public static void main(String[] args) {
        Game azul = new Game();
    }
}
