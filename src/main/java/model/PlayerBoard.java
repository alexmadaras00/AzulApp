package model;

import dataobjects.PlayerBoardState;
import dataobjects.ScoreChange;
import utils.ExceptionInvalidOperation;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
    private List<ScoreChange> scoreChanges;
    private Wall wall;
    private int score;
    private FloorLine floorLine;
    private PatternLine patternLine;

    public PlayerBoard(Wall wall, PatternLine patternLine, FloorLine floorLine) {
        this.wall = wall;
        this.patternLine = patternLine;
        this.floorLine = floorLine;
    }

    public PlayerBoard() {
        floorLine = new FloorLine();
        wall = new Wall();
        patternLine = new PatternLine();
        score = 0;
        scoreChanges = new ArrayList<>();
    }

    public PlayerBoard(Wall wall, PatternLine patternLine, FloorLine floorLine, int score, List<ScoreChange> scoreChanges) {
        this.wall = wall;
        this.patternLine = patternLine;
        this.floorLine = floorLine;
        this.score = score;
        this.scoreChanges = scoreChanges;
    }

    public boolean canAddTypePatternLine(int rowIndex, Color type) {
        return wall.canAddTile(rowIndex, type) && patternLine.canAddTile(rowIndex, type);
    }

    public void performMovePatternLine(int rowIndex, List<Tile> tiles) {
        if (canAddTypePatternLine(rowIndex, (Color) tiles.get(0)))
            patternLine.addTiles(rowIndex, tiles);
        else throw new ExceptionInvalidOperation("Pattern Line Invalid move. Cannot add a tile in the current format.");
    }

    public void performMoveFloorLine(List<Tile> tiles) {
        floorLine.addTiles(tiles);
    }

    public List<Tile> wallTilting() {
        List<Integer> completedRows = patternLine.completedRows();
        List<Tile> remainderList = new ArrayList<>();
        completedRows.forEach(completedRow -> {
                    List<Tile> tilesCompleted = patternLine.getCopyTable().get(completedRow);
                    Tile wallTile = tilesCompleted.get(0);
                    remainderList.addAll(tilesCompleted.subList(1, tilesCompleted.size()));
                    ScoreChange wallTileScoreChange = new ScoreChange();
                    wallTileScoreChange.setIsCompletionScore(false);
                    wallTileScoreChange.setHasColor(true);
                    wallTileScoreChange.setHasRowIndex(true);
                    wallTileScoreChange.setIndex(completedRow);
                    wallTileScoreChange.setColor((Color) wallTile);
                    wallTileScoreChange.setScoreDifference(wall.addTile(completedRow, wallTile));
                }
        );
        remainderList.addAll(floorLine.getCopyTiles());

        floorLine.clearTiles();
        ScoreChange floorLineScoreChange = new ScoreChange();
        floorLineScoreChange.setIsCompletionScore(false);
        floorLineScoreChange.setHasColor(false);
        floorLineScoreChange.setHasRowIndex(false);
        floorLineScoreChange.setScoreDifference(floorLine.getScore());

        return remainderList;
    }

    public void addFinalScores() {
        List<ScoreChange> scoreChangesList = wall.getCompletionScores();
        scoreChangesList.forEach(scoreChange ->
                this.score += scoreChange.getScoreDifference());
        this.scoreChanges = scoreChangesList;
    }

    public PlayerBoardState toObject() {
        PlayerBoardState playerBoardState = new PlayerBoardState();
        playerBoardState.setWall(wall.getCopyTable());
        playerBoardState.setFloorLine(floorLine.getCopyTiles());
        playerBoardState.setPatternLine(patternLine.getCopyTable());
        playerBoardState.setScoreChanges(scoreChanges);
        playerBoardState.setScore(score);
        return playerBoardState;
    }
}
