package model.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Tile;
import model.TileColor;

public class Factory implements FactoryInterface {
    private List<TileColor> tiles = new ArrayList<>();

    public List<TileColor> getAllTiles() {
        return tiles;
    }

    private List<TileColor> getTiles(TileColor type) {
        return tiles.stream()
                .filter(tile -> tile == type)
                .collect(Collectors.toList());
    }

    public List<TileColor> popAllTiles() {
        List<TileColor> copyTiles = List.copyOf(tiles);
        tiles.removeIf((t) -> (true));
        return copyTiles;
    }

    public List<TileColor> popTiles(TileColor tile) {
        List<TileColor> removeTiles = this.getTiles(tile);
        tiles.removeAll(removeTiles);
        return removeTiles;
    }

    public void addTiles(List<TileColor> tileList) {
        tiles.addAll(tileList);
    }

    public boolean hasTiles(TileColor type) {
        return tiles.contains(type);
    }

}
