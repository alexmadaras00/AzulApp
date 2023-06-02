package model;

import dataobjects.*;

import javax.xml.crypto.Data;
import java.util.List;

public interface Model extends Executable {
    GameState startGame();
    RoundUpdate startRound();
    RoundUpdate endRound();
    GameState terminateGame();
    GameState endGame();
    boolean isCurrentPlayer(PlayerData player);
    PlayerData addPlayer(PlayerData playerData);
    boolean isValidStartGame();
    DataObject performMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, TileColor tileColor);
    DataObject performMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex);
    DataObject performMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor);
    DataObject performMoveMiddleFloorLine(List<Tile> tiles);
    DataObject performMovePatternLineFloorLine(List<Tile> tiles, int patternLineRow, TileColor tileColor);
    boolean isValidMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, TileColor tileColor);
    boolean isValidMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex);
    boolean isValidMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor);
    boolean isValidMoveMiddleFloorLine(List<Tile> tiles);
    boolean isValidMovePatternLineFloorLine(List<Tile> tiles, int patternLineRow, TileColor tileColor);


}
