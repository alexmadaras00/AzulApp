package controller;

import model.Model;
import shared.EventType;
import shared.Location;
import shared.TileColor;


public interface Controller {
    void joinPlayer(String name);
    void startGame();
    void performMove(Location from, Location to, int fromIndex, int toIndex, int playerID, TileColor color);
    void terminateGame();
    void addListener(ControllerEventListener listener);
    void removeListener(ControllerEventListener listener);
    void notifyListeners(EventType type, String message);
    ModelProxy getProxy();

}
