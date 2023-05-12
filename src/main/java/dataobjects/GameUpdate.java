package dataobjects;

import java.util.List;
import java.util.Optional;

import model.Tile;

public class GameUpdate implements DataObject {
    private List<PlayerData> players;
    private Optional<List<List<Tile>>> factories;
    private Optional<List<Tile>> middle;
    private Move move;
    private int nextPlayer;

    public GameUpdate() {
    }
    public GameUpdate(List<PlayerData> players, Optional<List<List<Tile>>> factories, Optional<List<Tile>> middle,
            Move move, int nextPlayer) {
        this.players = players;
        this.factories = factories;
        this.middle = middle;
        this.move = move;
        this.nextPlayer = nextPlayer;
    }
    public List<PlayerData> getPlayers() {
        return players;
    }
    public void setPlayers(List<PlayerData> players) {
        this.players = players;
    }
    public Optional<List<List<Tile>>> getFactories() {
        return factories;
    }
    public void setFactories(Optional<List<List<Tile>>> factories) {
        this.factories = factories;
    }
    public Optional<List<Tile>> getMiddle() {
        return middle;
    }
    public void setMiddle(Optional<List<Tile>> middle) {
        this.middle = middle;
    }
    public Move getMove() {
        return move;
    }
    public void setMove(Move move) {
        this.move = move;
    }
    public int getNextPlayer() {
        return nextPlayer;
    }
    public void setNextPlayer(int nextPlayer) {
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
        if (move == null) {
            if (other.move != null)
                return false;
        } else if (!move.equals(other.move))
            return false;
        if (nextPlayer != other.nextPlayer)
            return false;
        return true;
    }
    
}
