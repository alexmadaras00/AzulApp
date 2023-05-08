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
        return tiles.stream()
                .filter(tile -> tile == type)
                .collect(Collectors.toList());
    }
    void addTiles(List<Tile> t){
        tiles.addAll(t);
    }
    boolean hasTiles(Color type){
        return tiles.contains(type);
    }
}
