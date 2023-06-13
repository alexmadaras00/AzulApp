package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    private final List<TileColor> tiles = new ArrayList<>();

    public List<TileColor> getTiles() {
        Collections.shuffle(tiles);
        return tiles;
    }

    public List<TileColor> popTiles(int count) {
        Collections.shuffle(tiles);
        List<TileColor> poppedTiles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            poppedTiles.add(tiles.get(tiles.size() - 1));
            tiles.remove(tiles.size() - 1);
        }
        return poppedTiles;
    }

    public void addTiles(List<TileColor> t) {
        this.tiles.addAll(t);
    }
}