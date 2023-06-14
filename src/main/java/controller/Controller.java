package controller;

import model.TileColor;

public interface Controller {
    void joinPlayer(String name);
    void startGame();
    void performMoveMiddleFloorLine(TileColor tileColor);
    void performMoveMiddlePatternLine(int row, TileColor tileColor);
    void performMoveFactoryFloorLine(int index, TileColor tileColor);
    void performMoveFactoryPatternLine(int index, int row, TileColor tileColor);
    void terminateGame();

}
