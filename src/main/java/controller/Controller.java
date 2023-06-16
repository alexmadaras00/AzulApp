package controller;

import javafx.util.Pair;
import model.TileColor;
import shared.EventType;
import shared.Location;
import shared.ModelProxy;


public interface Controller {
    void joinPlayer(String name);
    void startGame();
    void performMove(Pair<Location, Integer> from, Pair<Location, Integer> to, int playerID, TileColor color);
    void terminateGame();
    void addListener(ControllerEventListener listener);
    void removeListener(ControllerEventListener listener);
    void notifyListeners(EventType type, String message);
    ModelProxy getProxy();

}
