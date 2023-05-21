package dataobjects;

import java.util.List;
import java.util.Objects;

import model.Tile;

public class PlayerBoardState implements DataObject {
    private PlayerData player;
    private int score;
    private List<ScoreChange> scoreChanges;
    private List<List<Tile>> patternLine;
    private List<Tile> floorLine;
    private List<List<Tile>> wall;

    public PlayerBoardState() {
    }

    public PlayerData getPlayer() {
        return player;
    }

    public void setPlayer(PlayerData player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<ScoreChange> getScoreChanges() {
        return scoreChanges;
    }

    public void setScoreChanges(List<ScoreChange> scoreChanges) {
        this.scoreChanges = scoreChanges;
    }

    public List<List<Tile>> getPatternLine() {
        return patternLine;
    }

    public void setPatternLine(List<List<Tile>> patternLine) {
        this.patternLine = patternLine;
    }

    public List<Tile> getFloorLine() {
        return floorLine;
    }

    public void setFloorLine(List<Tile> floorLine) {
        this.floorLine = floorLine;
    }

    public List<List<Tile>> getWall() {
        return wall;
    }

    public void setWall(List<List<Tile>> wall) {
        this.wall = wall;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PlayerBoardState))
            return false;
        PlayerBoardState other = (PlayerBoardState) obj;
        return Objects.equals(player, other.player) &&
                score == other.score &&
                Objects.equals(scoreChanges, other.scoreChanges) &&
                Objects.equals(patternLine, other.patternLine) &&
                Objects.equals(floorLine, other.floorLine) &&
                Objects.equals(wall, other.wall);
    }

}
