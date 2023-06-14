package controller;

import model.TileColor;
import view.Location;


public interface Controller {
    void joinPlayer(String name);
    void startGame();

    void performMove(Location from, Location to, int fromIndex, int toIndex, int playerID, TileColor color);

    void terminateGame();

}
