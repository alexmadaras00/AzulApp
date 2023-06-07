package messaging.dataobjects;

import java.util.List;

import model.Tile;

public class PlayerBoardState implements DataObject {
    private PlayerData player;
    private int score;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerBoardState)) return false;

        PlayerBoardState that = (PlayerBoardState) o;

        if (getScore() != that.getScore()) return false;
        if (getPlayer() != null ? !getPlayer().equals(that.getPlayer()) : that.getPlayer() != null) return false;
        if (getPatternLine() != null ? !getPatternLine().equals(that.getPatternLine()) : that.getPatternLine() != null)
            return false;
        if (getFloorLine() != null ? !getFloorLine().equals(that.getFloorLine()) : that.getFloorLine() != null)
            return false;
        return getWall() != null ? getWall().equals(that.getWall()) : that.getWall() == null;
    }

}
