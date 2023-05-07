package model;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private List<Tile> tiles;

    List<Tile> getTiles(){
        return tiles;
    }
    List<Tile> popTiles(int count){
        List<Tile> poppedTiles = tiles.subList(0, count);
        poppedTiles.clear();
        return poppedTiles;
    }
    void addTiles(List<Tile> t){
        tiles.addAll(t);
    }
}
