package model.factory;

import java.util.List;

import model.TileColor;

public interface FactoryInterface {

    public List<TileColor> getAllTiles();

    public List<TileColor> popAllTiles();

    public List<TileColor> popTiles(TileColor tile);

    public void addTiles(List<TileColor> tiles);

    public boolean hasTiles(TileColor type);

}
