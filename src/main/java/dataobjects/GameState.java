package dataobjects;

import java.util.List;

import model.Tile;

public class GameState implements DataObject {
    private List<PlayerData> players;
    private List<List<Tile>> factories;
    private List<Tile> middle;
    private int currentPlayer;

    public GameState() {
    }
    public GameState(List<PlayerData> players, List<List<Tile>> factories, List<Tile> middle, int currentPlayer) {
        this.players = players;
        this.factories = factories;
        this.middle = middle;
        this.currentPlayer = currentPlayer;
    }
    public List<PlayerData> getPlayers() {
        return players;
    }
    public void setPlayers(List<PlayerData> players) {
        this.players = players;
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
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
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
        if (players == null) {
            if (other.players != null)
                return false;
        } else if (!players.equals(other.players))
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
        if (currentPlayer != other.currentPlayer)
            return false;
        return true;
    }
    
}
