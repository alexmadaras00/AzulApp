package dataobjects;

import java.util.List;

public class MoveUpdate implements DataObject {
    private PlayerData player;
    private List<Action> steps;
    private PlayerData nextPlayer;

    public MoveUpdate() {
    }

    public PlayerData getPlayer() {
        return player;
    }

    public void setPlayer(PlayerData player) {
        this.player = player;
    }

    public List<Action> getSteps() {
        return steps;
    }

    public void setSteps(List<Action> steps) {
        this.steps = steps;
    }

    public PlayerData getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(PlayerData nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveUpdate)) return false;

        MoveUpdate that = (MoveUpdate) o;

        if (getPlayer() != null ? !getPlayer().equals(that.getPlayer()) : that.getPlayer() != null) return false;
        if (getSteps() != null ? !getSteps().equals(that.getSteps()) : that.getSteps() != null) return false;
        return getNextPlayer() != null ? getNextPlayer().equals(that.getNextPlayer()) : that.getNextPlayer() == null;
    }

    @Override
    public int hashCode() {
        int result = getPlayer() != null ? getPlayer().hashCode() : 0;
        result = 31 * result + (getSteps() != null ? getSteps().hashCode() : 0);
        result = 31 * result + (getNextPlayer() != null ? getNextPlayer().hashCode() : 0);
        return result;
    }
}
