package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Middle {
    private final List<Tile> tiles = new ArrayList<>();

    public List<Tile> getAllTiles(){
        return tiles;
    }
    public List<Tile> getTiles(Color type){
        return new ArrayList<>();
    }
    public void addTiles(List<Tile> t){

    }
    public boolean hasTiles(Color type){
        return false;
    }
}
