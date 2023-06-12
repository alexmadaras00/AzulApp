package model.factory.twinteam;

import java.util.Collection;
import java.util.HashSet;


public class FactoryDisplay extends TileCollection implements Grabable, Emptiable {

    public FactoryDisplay() {
        super(4, new HashSet<>());
    }

    @Override
    public boolean addTiles(Collection<Tile> newTiles) throws CollectionOverCapacityException {
        if (this.getCapacity() < this.getSize() + newTiles.size()) {
            throw new CollectionOverCapacityException(
                    "FactoryDisplay can not be hold more than 4 tiles");
        }

        return this.tiles.addAll(newTiles);
    }

    @Override
    public void setTiles(Collection<Tile> newTiles)  throws CollectionOverCapacityException {
        if (this.getCapacity() < newTiles.size()) {
            throw new CollectionOverCapacityException(
                    "FactoryDisplay can not be hold more than 4 tiles");
        }

        tiles = newTiles;
    }

    @Override
    public void empty() {
        tiles.clear();
    }

    @Override
    public void emptyInto(TileCollection collection) throws CollectionOverCapacityException {
        collection.addTiles(tiles);
        tiles.clear();
    }

    @Override
    public Collection<Tile> grab(TileColour colour) {
        var grabbedTiles = new HashSet<Tile>();
        this.tiles.stream().filter(tile -> tile.getColour() == colour).forEach(grabbedTiles::add);
        grabbedTiles.forEach(tile -> tiles.remove(tile));
        return grabbedTiles;
    }
}
