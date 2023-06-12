package view;

import java.util.List;

import messaging.executors.Executable;
import model.Tile;
import model.TileColor;

public interface UI extends Executable {

    // GENERAL
    void startGame();

    void connectMessager(Messager messager);

    void showToast(String message);

    // confirm in hub, add to gamepage, show toast
    void addPlayer(int playerID, String name);

    // reenable join button hub, show toast
    void notAddPlayer(String playerName);

    // set factoies in GUI to id and disable the rest.
    void factorySetup(List<Integer> factoryIDs);

    // Factory

    void setTilesFactory(int factoryID, List<TileColor> tiles);

    void clearFactory(int factoryID);

    // Middle
    void addTileMiddle(Tile tile);

    void removeTilesMiddle(Tile tile);

    void clearMiddle();

    // PLAYER SPECIFIC

    void setPlayerName(int playerID, String name);

    void setScore(int playerID, int score);

    void setCurrentPlayer(int playerID);

    // wall
    void addTileWall(int PlayerID, int row, Tile tile);

    // paternline
    void addTilePattern(int playerID, int row, Tile tile);

    void removeTilePattern(int playerID, int row, Tile tile);

    void clearPatternLine(int playerID, int row);

    void addTileFloorLine(int playerID, Tile tile);

    void clearFloorLine(int playerID);

}