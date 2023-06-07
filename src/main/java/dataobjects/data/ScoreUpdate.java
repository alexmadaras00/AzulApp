package dataobjects.data;

import java.util.List;

import dataobjects.DataObject;

public class ScoreUpdate implements DataObject {
    private PlayerData player;
    private int newScore;
    private List<ScoreChange> scoreChanges;

    public ScoreUpdate() {
    }

    public PlayerData getPlayer() {
        return player;
    }

    public void setPlayer(PlayerData player) {
        this.player = player;
    }

    public int getNewScore() {
        return newScore;
    }

    public void setNewScore(int newScore) {
        this.newScore = newScore;
    }

    public List<ScoreChange> getScoreChanges() {
        return scoreChanges;
    }

    public void setScoreChanges(List<ScoreChange> scoreChanges) {
        this.scoreChanges = scoreChanges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScoreUpdate)) return false;

        ScoreUpdate that = (ScoreUpdate) o;

        if (getNewScore() != that.getNewScore()) return false;
        if (getPlayer() != null ? !getPlayer().equals(that.getPlayer()) : that.getPlayer() != null) return false;
        return getScoreChanges() != null ? getScoreChanges().equals(that.getScoreChanges()) : that.getScoreChanges() == null;
    }

    @Override
    public int hashCode() {
        int result = getPlayer() != null ? getPlayer().hashCode() : 0;
        result = 31 * result + getNewScore();
        result = 31 * result + (getScoreChanges() != null ? getScoreChanges().hashCode() : 0);
        return result;
    }
}
