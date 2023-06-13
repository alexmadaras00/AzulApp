package model;

import messaging.dataobjects.DataObject;
import messaging.dataobjects.GameState;
import messaging.dataobjects.PlayerData;
import model.factory.Factory;
import utils.ExceptionGameStart;

import java.util.List;

public interface Model extends ModelProxy{

    void startGame() throws ExceptionGameStart;

    void terminateGame();

    void addPlayer(Player player);

    boolean canStartGame();

    void performMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor);

    void performMoveFactoryFloorLine(int factoryIndex, TileColor tileColor);

    void performMoveMiddlePatternLine(int patternLineRow, TileColor tileColor);

    void performMoveMiddleFloorLine(TileColor tileColor);

    boolean isValidMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor);

    boolean isValidMoveFactoryFloorLine(int factoryIndex, TileColor tileColor);

    boolean isValidMoveMiddlePatternLine(int patternLineRow, TileColor tileColor);

    boolean isValidMoveMiddleFloorLine(TileColor tileColor);
}
