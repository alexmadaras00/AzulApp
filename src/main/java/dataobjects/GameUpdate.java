package dataobjects;

import java.util.List;
import java.util.Objects;

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
        if (obj == null || !(obj instanceof GameUpdate))
            return false;
        GameUpdate other = (GameUpdate) obj;
        return Objects.equals(playerBoards, other.playerBoards) &&
                Objects.equals(factories, other.factories) &&
                Objects.equals(middle, other.middle) &&
                Objects.equals(move, other.move) &&
                Objects.equals(nextPlayer, other.nextPlayer);
    }
}
