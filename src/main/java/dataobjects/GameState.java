package dataobjects;

import model.*;

import java.util.List;


public class GameState implements DataObject {
    private List<PlayerBoardState> playerBoards;
    private List<List<Tile>> factories;
    private List<Tile> middle;
    private PlayerData currentPlayer;
    private PlayerData winnerPlayer;

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

    public PlayerData getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setWinnerPlayer(PlayerData winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameState other = (GameState) obj;
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
        if (currentPlayer == null) {
            if (other.currentPlayer != null)
                return false;
        } else if (!currentPlayer.equals(other.currentPlayer))
            return false;
        if (winnerPlayer == null) {
            if (other.winnerPlayer != null)
                return false;
        } else if (!winnerPlayer.equals(other.winnerPlayer))
            return false;
        return true;
    }
}
