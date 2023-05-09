package model;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private final List<Tile> tiles = new ArrayList<>();

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Tile> popTiles(int count) {
        List<Tile> poppedTiles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            poppedTiles.add(tiles.get(tiles.size()-1));
            tiles.remove(tiles.size() - 1);
        }
        return poppedTiles;
    }

    public void addTiles(List<Tile> t) {
        this.tiles.addAll(t);
    }
}