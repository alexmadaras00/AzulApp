package model;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
    private Integer score;
    private Wall wall;
    private FloorLine floorLine;
    private PatternLine patternLine;

    boolean canAddTypePattern(int rowIndex, Color type){
        return false;
    }
    List<Tile> wallTilting(){
        return new ArrayList<>();
    }
    void addFinalScores(){

    }
}
