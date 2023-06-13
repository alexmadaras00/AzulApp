package model;


import messaging.dataobjects.*;

import java.util.ArrayList;
import java.util.HashMap;
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
        return wall.canAddTile(rowIndex, type) && patternLine.canAddTile(rowIndex, type);}
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

    public HashMap<Location, List<Tile>> wallTilling() {
        List<Integer> completedRows = this.patternLine.completedRows();
        HashMap<Location, List<Tile>> remainderTable = new HashMap<>();
        completedRows.forEach(completedRow -> {
                    List<Tile> tilesCompleted = patternLine.getCopyTable().get(completedRow);
                    Tile wallTile = tilesCompleted.get(0);
                    // remainderTable.put(new Location(LocationType.PATTERN_LINE, completedRow), tilesCompleted.subList(1, tilesCompleted.size()));
                    remainderTable.put(new Location(LocationType.PATTERN_LINE, completedRow), tilesCompleted);
                    int wallTileScoreDifference = wall.addTile(completedRow, wallTile);
                    score += wallTileScoreDifference;
                    ScoreChange wallTileScoreChange = new ScoreChange();
                    wallTileScoreChange.setType(ScoreType.PLACED_TILE_IN_WALL);
                    wallTileScoreChange.setIndex(completedRow);
                    wallTileScoreChange.setColor((TileColor) wallTile);
                    wallTileScoreChange.setScoreDifference(wallTileScoreDifference);
                    scoreChanges.add(wallTileScoreChange);
                }
        );
        remainderTable.put(new Location(LocationType.FLOOR_LINE, 0), floorLine.getCopyTiles());

        int floorLineScoreDifference = floorLine.getScore();
        floorLineScoreDifference = Math.max(floorLineScoreDifference, -score);
        score += floorLineScoreDifference;

        ScoreChange floorLineScoreChange = new ScoreChange();
        floorLineScoreChange.setType(ScoreType.CLEARED_FLOOR_LINE);
        floorLineScoreChange.setScoreDifference(floorLineScoreDifference);
        scoreChanges.add(floorLineScoreChange);

        completedRows.forEach(completedRow -> patternLine.clearTiles(completedRow));
        floorLine.clearTiles();

        return remainderTable;
    }

    public boolean hasFulfilledEndCondition(){
        return wall.getCompletedRowCount()>0;
    }

    public void addFinalScores() {
        List<ScoreChange> scoreChangesWallList = wall.getCompletionScores();
        scoreChanges.addAll(scoreChangesWallList);
        scoreChangesWallList.forEach(scoreChange ->
                this.score += scoreChange.getScoreDifference());
    }


    public PlayerBoardState toObject() {
        PlayerBoardState playerBoardState = new PlayerBoardState();
        playerBoardState.setWall(wall.getCopyTable());
        playerBoardState.setFloorLine(floorLine.getCopyTiles());
        playerBoardState.setPatternLine(patternLine.getCopyTable());
        playerBoardState.setScore(score);

        return playerBoardState;
    }

    public List<ScoreChange> popAllScoreChanges() {
        List<ScoreChange> poppedScoreChanges = scoreChanges;
        scoreChanges = new ArrayList<>();
        return poppedScoreChanges;
    }

    public int getCompletedRowCount() {
        return wall.getCompletedRowCount();
    }
}
