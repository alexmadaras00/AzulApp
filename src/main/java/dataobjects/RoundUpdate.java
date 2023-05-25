package dataobjects;

import java.util.List;

public class RoundUpdate implements DataObject {
    private List<ScoreUpdate> scoreUpdates;
    private List<Action> updates;
    private MoveUpdate move;

    public RoundUpdate() {
    }

    public List<ScoreUpdate> getScoreUpdates() {
        return scoreUpdates;
    }

    public void setScoreUpdates(List<ScoreUpdate> scoreUpdates) {
        this.scoreUpdates = scoreUpdates;
    }

    public List<Action> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Action> updates) {
        this.updates = updates;
    }

    public MoveUpdate getMove() {
        return move;
    }

    public void setMove(MoveUpdate move) {
        this.move = move;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoundUpdate)) return false;

        RoundUpdate that = (RoundUpdate) o;

        if (getScoreUpdates() != null ? !getScoreUpdates().equals(that.getScoreUpdates()) : that.getScoreUpdates() != null)
            return false;
        if (getUpdates() != null ? !getUpdates().equals(that.getUpdates()) : that.getUpdates() != null) return false;
        return getMove() != null ? getMove().equals(that.getMove()) : that.getMove() == null;
    }

    @Override
    public int hashCode() {
        int result = getScoreUpdates() != null ? getScoreUpdates().hashCode() : 0;
        result = 31 * result + (getUpdates() != null ? getUpdates().hashCode() : 0);
        result = 31 * result + (getMove() != null ? getMove().hashCode() : 0);
        return result;
    }
}
