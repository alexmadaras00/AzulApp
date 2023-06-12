package model.factory.twinteam;

import java.util.ArrayList;
import java.util.Collection;

public abstract class TileCollection {

    protected Collection<Tile> tiles;
    protected final int capacity;

    public TileCollection() {
        this.tiles = new ArrayList<Tile>();
        this.capacity = 101;
    }

    public TileCollection(int capacity) {
        this.tiles = new ArrayList<Tile>();
        this.capacity = capacity;
    }

    public TileCollection(Collection<Tile> tiles) {
        this.tiles = tiles;
        this.capacity = 101;
    }

    public TileCollection(int capacity, Collection<Tile> tiles) {
        this.tiles = tiles;
        this.capacity = capacity;
    }

    public abstract boolean addTiles(Collection<Tile> tiles) throws CollectionOverCapacityException;

    public abstract void setTiles(Collection<Tile> tiles) throws CollectionOverCapacityException;

    public Collection<Tile> getTiles() {
        return this.tiles;
    }
    ;

    public int getCapacity() {
        return this.capacity;
    }
    ;

    public int getSize() {
        return this.tiles.size();
    }
    ;
}
