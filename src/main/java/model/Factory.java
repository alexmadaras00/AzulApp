package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Factory {
    public List<Tile> tiles;

    List<Tile> getTiles(Color type) {
        return new ArrayList<>();
    }

    void addTiles(List<Tile> t) {

    }

    boolean hasTiles(Color type) {
        return false;
    }

}
