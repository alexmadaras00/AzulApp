package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Factory {
    private final List<Tile> tiles = new ArrayList<>();

    public List<Tile> getAllTiles(){
        return tiles;
    }
    public List<Tile> getTiles(Color type){
        return tiles.stream()
                .filter(tile -> tile == type)
                .collect(Collectors.toList());
    }
    public void addTiles(List<Tile> t){
      tiles.addAll(t);
    }
    public boolean hasTiles(Color type){
        return tiles.contains(type);
    }

}