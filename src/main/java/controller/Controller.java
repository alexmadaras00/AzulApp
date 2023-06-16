package controller;

import model.Model;
import model.TileColor;


public interface Controller {
    void joinPlayer(String name);
    void startGame();
    void performMove(Location from, Location to, int fromIndex, int toIndex, int playerID, TileColor color);
    void terminateGame();
    void addListener(ControllerEventListener listener);
    void removeListener(ControllerEventListener listener);
    void notifyListeners(EventType type, String message);
    void setModel(Model model);

}
