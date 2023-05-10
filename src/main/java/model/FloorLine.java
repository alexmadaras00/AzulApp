package model;

import java.util.List;

public class FloorLine {
    private List<Tile> tiles;
    private List<Integer> scores;

    public void addTiles(List<Tile> t){
        if (tiles.size()<scores.size()) {
            tiles.addAll(t);
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