package model;

import dataobjects.PlayerBoardState;
import dataobjects.ScoreChange;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
    private List<ScoreChange> scoreChanges;
    private Wall wall;
    private int score;
    private FloorLine floorLine;
    private PatternLine patternLine;

    public PlayerBoard() {
        floorLine = new FloorLine();
        wall = new Wall();
        patternLine = new PatternLine();
        score = 0;
        scoreChanges = new ArrayList<>();
    }
    public FloorLine getFloorLine() {
        return floorLine;
    }
    public PatternLine getPatternLine() {
        return patternLine;
    }
    public Wall getWall() {
        return wall;
    }
    public int getScore() {
        return score;
    }
    public List<ScoreChange> getScoreChanges() {
        return scoreChanges;
    }

    public boolean canAddTypePatternLine(int rowIndex, TileColor type) {

        return wall.canAddTile(rowIndex, type) && patternLine.canAddTile(rowIndex, type);
    }
    public List<Tile> performMovePatternLine(int rowIndex, List<Tile> tiles) {
        tiles.forEach(tile -> {
            if (!canAddTypePatternLine(rowIndex, (TileColor) tile))
                throw new RuntimeException("This tile cannot be added on the current pattern line.");
        });
        return patternLine.addTiles(rowIndex, tiles);
    }
    public List<Tile> performMoveFloorLine(List<Tile> tiles) {
        return floorLine.addTiles(tiles);
    }

    public List<Tile> wallTilling() {
        List<Integer> completedRows = this.patternLine.completedRows();
        List<Tile> remainderList = new ArrayList<>();
        completedRows.forEach(completedRow -> {
                    List<Tile> tilesCompleted = patternLine.getCopyTable().get(completedRow);
                    Tile wallTile = tilesCompleted.get(0);
                    remainderList.addAll(tilesCompleted.subList(1, tilesCompleted.size()));
                    ScoreChange wallTileScoreChange = new ScoreChange();
                    wallTileScoreChange.setIndex(completedRow);
                    wallTileScoreChange.setColor((TileColor) wallTile);
                    wallTileScoreChange.setScoreDifference(wall.addTile(completedRow, wallTile));
                }
        );
        remainderList.addAll(floorLine.getCopyTiles());
        floorLine.clearTiles();
        ScoreChange floorLineScoreChange = new ScoreChange();

        floorLineScoreChange.setScoreDifference(floorLine.getScore());
        completedRows.forEach(completedRow -> patternLine.clearTiles(completedRow));

        return remainderList;
    }
    public boolean hasFulfilledEndCondition(){
        return wall.hasCompleteRow();
    }

    public void addFinalScores() {
        List<ScoreChange> scoreChangesWallList = wall.getCompletionScores();
        int scoreFloorLine = floorLine.getScore();
        scoreChangesWallList.forEach(scoreChange ->
                this.score += scoreChange.getScoreDifference());
        this.score -= scoreFloorLine;
        this.scoreChanges = scoreChangesWallList;
    }

    public PlayerBoardState toObject() {
        PlayerBoardState playerBoardState = new PlayerBoardState();
        playerBoardState.setWall(wall.getCopyTable());
        playerBoardState.setFloorLine(floorLine.getCopyTiles());
        playerBoardState.setPatternLine(patternLine.getCopyTable());
        playerBoardState.setScore(score);

        return playerBoardState;
    }
}
