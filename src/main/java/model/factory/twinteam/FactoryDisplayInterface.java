package model.factory.twinteam;


import java.util.Collection;

public interface FactoryDisplayInterface {

    public boolean addToTiles(Collection<Tile> tiles);

    public void setTiles(Collection<Tile> tiles);

    public Collection<Tile> grab(TileColour colour);

    public void empty();

    public void emptyInto(TileCollection collection);

    public int getSize();

    public int getCapacity();

    public Collection<Tile> getTiles();

}