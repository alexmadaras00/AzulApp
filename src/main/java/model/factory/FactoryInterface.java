package model.factory;

import java.util.List;

import model.Tile;
import model.TileColor;

public interface FactoryInterface {

    public List<Tile> getAllTiles();

    public List<Tile> popAllTiles();

    public List<Tile> popTiles(TileColor tile);

    public void addTiles(List<Tile> t);

    public boolean hasTiles(TileColor type);

}
