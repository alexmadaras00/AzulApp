package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FloorLine {
    private List<Tile> tiles = new ArrayList<>();
    private final List<Integer> scores = Arrays.asList(-1,-1,-2,-2,-2,-3,-3);

    public List<Tile> addTiles(List<Tile> t){
        List<Tile> excessTiles = new ArrayList<>();
        if (t.contains(PlayerTile.getInstance())) {
            if (tiles.size() == 7) { excessTiles.add(tiles.remove(6)); }
            tiles.add(0, PlayerTile.getInstance());
        }
        for (Tile tile : t) {
            if (tile == PlayerTile.getInstance()) continue;
            if (tiles.size() < 7) {
                tiles.add(tile);
            } else { excessTiles.add(tile);}
        }
        return excessTiles;
    }
    public int getScore(){
        Integer penalty = 0;
        for (var i = 0; i < tiles.size(); i++) {
            penalty+=scores.get(i);
        }
        return penalty;
    }

    public List<Tile> getCopyTiles(){
        return Collections.unmodifiableList(tiles);
    }

    public List<Tile> clearTiles(){
        List<Tile> clearedTiles = tiles;
        tiles = (new ArrayList<>());
        return clearedTiles;
    }
}