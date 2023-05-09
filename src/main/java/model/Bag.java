package model;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private final List<Tile> tiles = new ArrayList<>();

    List<Tile> getTiles() {
        return tiles;
    }

    List<Tile> popTiles(int count) {
        return new ArrayList<>();
    }

    void addTiles(List<Tile> t) {

    }
}
