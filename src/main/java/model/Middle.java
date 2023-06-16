package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Middle {
    private final List<Tile> tiles = new ArrayList<>();

    public List<Tile> getAllTiles(){
        return tiles;
    }
    public List<Tile> getTiles(TileColor type){
        return tiles.stream()
                .filter(tile -> tile == type)
                .collect(Collectors.toList());
    }

    public List<Tile> popAllTiles() {
        List<Tile> copyTiles = List.copyOf(tiles);
        tiles.removeIf((t) -> (true));
        return copyTiles;
    }

    public List<Tile> popTiles(TileColor tile) {
        List<Tile> removeTiles = this.getTiles(tile);
        tiles.removeAll(removeTiles);
        return removeTiles;
    }

    public void addTiles(List<Tile> t){
        tiles.addAll(t);
    }
    public boolean hasTiles(TileColor type){
        return tiles.contains(type);
    }

    public boolean hasPlayerTile() {
        return tiles.contains(PlayerTile.getInstance());
    }

    public Tile popPlayerTile() {
        return tiles.remove(PlayerTile.getInstance()) ? PlayerTile.getInstance() : null;
    }
}
