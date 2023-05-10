package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FloorLine {
    private final List<Tile> tiles = new ArrayList<>();
    private final List<Integer> scores = Arrays.asList(-1,-1,-2,-2,-2,-3,-3);

    public void addTiles(List<Tile> t){
        for(int i=0; i<t.size(); i++) {
            if (tiles.size() < 7) {
                tiles.add(t.get(i));
            }
        }
    }
    public int getScore(){
        Integer penalty = 0;
        for (var i = 0; i < tiles.size(); i++) {
            penalty+=scores.get(i);
        }
        return penalty;
    }
    public List<Tile> getCopyTiles(){
        return tiles;
    }
    public List<Tile> clearTiles(){
        tiles.removeAll(tiles);
        return tiles;
    }
}