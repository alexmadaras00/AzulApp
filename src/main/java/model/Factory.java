package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Factory {
    public List<Tile> tiles;

    List<Tile> getTiles(Color type) {
        return tiles.stream()
                .filter(tile -> tile == type)
                .collect(Collectors.toList());

    }

    void addTiles(List<Tile> t) {
        tiles.addAll(t);
    }

    boolean hasTiles(Color type) {
        return tiles.contains(type);
    }

}
