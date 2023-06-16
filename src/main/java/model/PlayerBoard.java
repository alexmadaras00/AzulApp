package model;

import java.util.ArrayList;
import java.util.List;

import shared.Tile;
import shared.TileColor;

public class PlayerBoard {
    private Wall wall;
    private int score;
    private FloorLine floorLine;
    private PatternLine patternLine;
    private Player player;

    public PlayerBoard() {
        floorLine = new FloorLine();
        wall = new Wall();
        patternLine = new PatternLine();
        score = 0;
        player = new Player();
    }
    public PlayerBoard(Player player) {
        floorLine = new FloorLine();
        wall = new Wall();
        patternLine = new PatternLine();
        score = 0;
        this.player = player;
    }
    // -- getters for testing --
    public FloorLine getFloorLine() {
        return floorLine;
    }
    public PatternLine getPatternLine() {
        return patternLine;
    }
    public Wall getWall() {
        return wall;
    }
    public Player getPlayer() {return player;}
    // -------------------------

    public int getScore() {
        return score;
    }

    public int getCompletedRowCount() {
        return wall.getCompletedRowCount();
    }

    public List<List<Tile>> getWallTiles() {
        return wall.getCopyTable();
    }

    public List<List<Tile>> getPatternLineTiles() {
        return patternLine.getCopyTable();
    }

    public List<Tile> getFloorLineTiles() {
        return floorLine.getCopyTiles();
    }
    public int getPlayerIdentifier(){return player.getIdentifier();}
    public String getPlayerName(){return player.getName();}

    public boolean hasFulfilledEndCondition(){
        return wall.getCompletedRowCount()>0;
    }

    public boolean canAddTypePatternLine(int rowIndex, TileColor type) {
        Boolean wallCan = wall.canAddTile(rowIndex, type);
        Boolean pattern = patternLine.canAddTile(rowIndex, type);
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
        List<Tile> remainderTiles = new ArrayList<>();
        completedRows.forEach(completedRow -> {
                    List<Tile> tilesCompleted = patternLine.getCopyTable().get(completedRow);
                    score += wall.addTile(completedRow, tilesCompleted.get(0));
                    remainderTiles.addAll(tilesCompleted.subList(1, tilesCompleted.size()));
                    patternLine.clearTiles(completedRow);
                }
        );
        score += Math.max(-score, floorLine.getScore());
        remainderTiles.addAll(floorLine.getCopyTiles());
        floorLine.clearTiles();
        return remainderTiles;
    }

    public void addFinalScores() {
        List<Integer> scoreChanges = wall.getCompletionScores();
        scoreChanges.forEach(scoreChange ->
                this.score += scoreChange);
    }


}