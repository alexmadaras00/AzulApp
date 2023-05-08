package model;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private final List<Tile> tiles = new ArrayList<>();

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Tile> popTiles ( int count){
            for (int i = 0; i < count; i++) {
                tiles.remove(tiles.size() - 1);
            }
            return tiles;
        }
        public void addTiles (List < Tile > t) {
            this.tiles.addAll(t);
        }
    }