package model;

import dataobjects.PlayerBoardState;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
    private int score;
    private List<Object> scoreChanges;
    private Wall wall;
    private FloorLine floorLine;
    private PatternLine patternLine;

    public boolean canAddTypePattern(int rowIndex, Color type){
        return false;
    }
    public void performMovePatternLine(int rowIndex, List<Tile> tiles){

    }
    public void performMoveFloorLine(List<Tile> tiles){

    }
    public List<Tile> wallTilting(){
        return new ArrayList<>();
    }
    public void addFinalScores(){

    }
    public PlayerBoardState toObject(){
        return new PlayerBoardState();
    }
}
