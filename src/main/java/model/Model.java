package model;

import dataobjects.*;

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
    MoveUpdate performMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, Color color);
    MoveUpdate performMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex);
    MoveUpdate performMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, Color color);
    MoveUpdate performMoveMiddleFloorLine(List<Tile> tiles);
    MoveUpdate performMovePatternLineFloorLine(List<Tile> tiles, int patternLineRow, Color color);
    boolean isValidMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, Color color);
    boolean isValidMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex);
    boolean isValidMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, Color color);
    boolean isValidMoveMiddleFloorLine(List<Tile> tiles);
    boolean isValidMovePatternLineFloorLine(List<Tile> tiles, int patternLineRow, Color color);


}
