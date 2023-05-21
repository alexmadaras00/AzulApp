package dataobjects;

import java.util.List;
import java.util.Objects;

import model.Tile;

public class GameState implements DataObject {
    private List<PlayerBoardState> playerBoards;
    private List<List<Tile>> factories;
    private List<Tile> middle;
    private PlayerData currentPlayer;

    public GameState() {
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

    public PlayerData getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerData currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof GameState))
            return false;
        GameState other = (GameState) obj;
        return Objects.equals(playerBoards, other.playerBoards) &&
                Objects.equals(factories, other.factories) &&
                Objects.equals(middle, other.middle) &&
                Objects.equals(currentPlayer, other.currentPlayer);
    }
}
