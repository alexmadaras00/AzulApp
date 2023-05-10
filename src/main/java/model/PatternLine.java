package model;


import java.util.ArrayList;
import java.util.List;

public class PatternLine {
    private List<List<Tile>> table;

    public void addTiles(int row, Tile tiles){

    }
    public Boolean canAddTile(int row, Color type){
        return false;
    }
    public void clearTiles(int row, Tile tiles){

    }
    public List<Integer> completedRows(){
        return new ArrayList<>();
    }
}
