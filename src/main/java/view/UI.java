package view;

import java.util.List;

import messaging.executors.Executable;
import model.Tile;
import model.TileColor;

public interface UI extends Executable {

    // GENERAL

    void connectMessager(Messager messager);

    void resetGameState();

    void clearAll();

    void commit();

    // Factory
    void addFactory(int factoryID);

    void addTileFactory(int factoryID, Tile tile);

    void removeTilesFactory(int factoryID, Tile tile);

    void clearFactory(int factoryID);

    // Middle
    void addTileMiddle(Tile tile);

    void removeTilesMiddle(Tile tile);

    void clearMiddle();

    // PLAYER SPECIFIC

    void addPlayer(int playerID, String name);

    void setPlayerName(int playerID, String name);

    void setScore(int playerID, int score);

    void setActivePlayerView(int playerID);

    void clearPlayer(int playerID);

    // wall
    void addTileWall(int PlayerID, int row, Tile tile);

    void removeTilesWall(int playerID, int row, Tile tile);

    void clearWall(int playerID);

    // paternline
    void addTilePattern(int playerID, int row, Tile tile);

    void removeTilePattern(int playerID, int row, Tile tile);

    void clearPatternLine(int playerID, int row);

    void clearPattern(int playerID);

    // floor
    void addTileFloorLine(int playerID, Tile tile);

    void removeTilesFloorLine(int playerID, Tile tile);

    void clearFloorLine(int playerID);

    void setWallPattern(List<List<TileColor>> pttern);
}
