package model;

import utils.ExceptionGameStart;

import java.util.List;

import messaging.*;
import messaging.dataobjects.DataObject;
import messaging.dataobjects.GameState;
import messaging.dataobjects.PlayerData;
import messaging.dataobjects.RoundUpdate;
import messaging.executors.Executable;

public interface Model extends Executable {
    GameState startGame() throws ExceptionGameStart;
    RoundUpdate startRound();
    RoundUpdate endRound();
    GameState terminateGame();
    GameState endGame();
    boolean isCurrentPlayer(PlayerData player);
    PlayerData addPlayer(Player player);
    boolean isValidStartGame();
    DataObject performMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, TileColor tileColor);
    DataObject performMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex);
    DataObject performMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor);
    DataObject performMoveMiddleFloorLine(List<Tile> tiles);
    boolean isValidMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, TileColor tileColor);
    boolean isValidMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex);
    boolean isValidMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor);
    boolean isValidMoveMiddleFloorLine(List<Tile> tiles);


}
