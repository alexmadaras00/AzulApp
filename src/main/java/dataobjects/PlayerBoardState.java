package dataobjects;

import java.util.List;

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
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayerBoardState other = (PlayerBoardState) obj;
        if (player == null) {
            if (other.player != null)
                return false;
        } else if (!player.equals(other.player))
            return false;
        if (score != other.score)
            return false;
        if (scoreChanges == null) {
            if (other.scoreChanges != null)
                return false;
        } else if (!scoreChanges.equals(other.scoreChanges))
            return false;
        if (patternLine == null) {
            if (other.patternLine != null)
                return false;
        } else if (!patternLine.equals(other.patternLine))
            return false;
        if (floorLine == null) {
            if (other.floorLine != null)
                return false;
        } else if (!floorLine.equals(other.floorLine))
            return false;
        if (wall == null) {
            if (other.wall != null)
                return false;
        } else if (!wall.equals(other.wall))
            return false;
        return true;
    }
    
    
}
