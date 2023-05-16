package dataobjects;

import java.util.List;

import model.Tile;

public class GameUpdate implements DataObject {
    private List<PlayerBoardState> playerBoards;
    private List<List<Tile>> factories;
    private List<Tile> middle;
    private Move move;
    private PlayerData nextPlayer;
    
    public GameUpdate() {
    }

    public List<PlayerBoardState> getPlayerBoards() {
        return playerBoards;
    }

    public void setPlayerBoards(List<PlayerBoardState> playerBoards) {
        this.playerBoards = playerBoards;
    }

    public List<List<Tile>> getFactories() {
        return factories;
    }

    public void setFactories(List<List<Tile>> factories) {
        this.factories = factories;
    }

    public List<Tile> getMiddle() {
        return middle;
    }

    public void setMiddle(List<Tile> middle) {
        this.middle = middle;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public PlayerData getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(PlayerData nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameUpdate other = (GameUpdate) obj;
        if (playerBoards == null) {
            if (other.playerBoards != null)
                return false;
        } else if (!playerBoards.equals(other.playerBoards))
            return false;
        if (factories == null) {
            if (other.factories != null)
                return false;
        } else if (!factories.equals(other.factories))
            return false;
        if (middle == null) {
            if (other.middle != null)
                return false;
        } else if (!middle.equals(other.middle))
            return false;
        if (move == null) {
            if (other.move != null)
                return false;
        } else if (!move.equals(other.move))
            return false;
        if (nextPlayer == null) {
            if (other.nextPlayer != null)
                return false;
        } else if (!nextPlayer.equals(other.nextPlayer))
            return false;
        return true;
    }
}
