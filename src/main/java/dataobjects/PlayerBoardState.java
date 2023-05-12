package dataobjects;

import java.util.List;

import model.Tile;

public class PlayerBoardState implements DataObject {
    private int score;
    private List<ScoreChange> scoreChanges;
    private List<List<Tile>> patternLine;
    private List<Tile> floorLine;
    private List<List<Tile>> wall;
}
