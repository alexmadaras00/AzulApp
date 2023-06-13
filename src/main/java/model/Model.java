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
    GameState terminateGame();
    boolean isCurrentPlayer(PlayerData player);

    PlayerData addPlayer(Player player);

    boolean isValidStartGame();

    List<Tile> getBox();

    List<Player> getTurnOrder();

    List<Player> getPlayers();

    Middle getMiddle();

    GamePhase getGamePhase();

    List<Factory> getFactories();

    Bag getBag();

    int getRound();

    Boolean isPlaying();

    DataObject performMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor);

    DataObject performMoveFactoryFloorLine(int factoryIndex, TileColor tileColor);

    DataObject performMoveMiddlePatternLine(int patternLineRow, TileColor tileColor);

    DataObject performMoveMiddleFloorLine(TileColor tileColor);

    boolean isValidMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor);

    boolean isValidMoveFactoryFloorLine(int factoryIndex, TileColor tileColor);

    boolean isValidMoveMiddlePatternLine(int patternLineRow, TileColor tileColor);

    boolean isValidMoveMiddleFloorLine(TileColor tileColor);
}
