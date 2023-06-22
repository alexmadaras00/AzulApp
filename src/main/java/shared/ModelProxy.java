package shared;

import java.util.List;

import model.Tile;

public interface ModelProxy {
    
    int getFactoryCount();

    Tile[] getFactory(int index);

    List<Tile> getMiddle();

    List<Player> getPlayers();

    String getName(int identifier);

    List<List<Tile>> getWall(int identifier);

    Tile[] getPatternLine(int identifier, int row);

    Tile[] getFloorLine(int identifier);

    int getScore(int identifier);

    List<Integer> getWinners();

    int getCurrentPlayer();

    int getRound();
    
    boolean isPlaying();
}
