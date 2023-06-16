package shared;

import java.util.List;

import model.Player;
import model.PlayerBoard;
import model.Tile;

public interface ModelProxy {
    
    int getFactoryCount();

    Tile[] getFactory(int index);

    List<Tile> getMiddle();

    List<Player> getPlayerList();

    List<PlayerBoard> getPlayerBoardList();

    String getName(int identifier);

    Tile getWall(int identifier, int row, int col);

    Tile[] getPatternLine(int identifier, int row);

    Tile[] getFloorLine(int identifier);

    int getScore(int identifier);

    List<Integer> getWinners();

    int getCurrentPlayer();

    int getRound();
    
    boolean isPlaying();
}
