package model;

import messaging.dataobjects.DataObject;
import messaging.dataobjects.GameState;
import messaging.dataobjects.PlayerData;
import messaging.dataobjects.RoundUpdate;
import messaging.executors.Executable;
import model.factory.Factory;
import utils.ExceptionGameStart;

import java.util.List;

public interface Model extends Executable {
    GameState startGame() throws ExceptionGameStart;

    RoundUpdate startRound();

    RoundUpdate endRound();

    GameState terminateGame();

    GameState endGame();

    boolean isCurrentPlayer(PlayerData player);

    PlayerData addPlayer(Player player);

    boolean isValidStartGame();

    DataObject performMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow,
            TileColor tileColor);

    DataObject performMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex);

    DataObject performMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor);

    DataObject performMoveMiddleFloorLine(List<Tile> tiles);

    boolean isValidMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, TileColor tileColor);

    boolean isValidMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex);

    boolean isValidMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor);

    boolean isValidMoveMiddleFloorLine(List<Tile> tiles);

    List<Tile> getBox();

    List<Player> getTurnOrder();

    List<Player> getPlayers();

    Middle getMiddle();

    GamePhase getGamePhase();

    List<Factory> getFactories();

    Bag getBag();

    int getRound();

    Boolean isPlaying();
}
