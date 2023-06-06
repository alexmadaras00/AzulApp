package model.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Tile;
import model.TileColor;
import model.factory.twinteam.FactoryDisplay;
import model.factory.twinteam.FactoryDisplayInterface;
import model.factory.twinteam.TileColour;

public class FactoryTwinteamWrapper implements FactoryInterface {
    private FactoryDisplayInterface factory;

    public FactoryTwinteamWrapper() {
        this.factory = new FactoryDisplay();
    }

    private TileColor translateTileColor(TileColour colour) {
        switch (colour) {
            case RED:
                return TileColor.RED;
            case GREEN:
                return TileColor.CYAN;
            case YELLOW:
                return TileColor.YELLOW;
            case BLUE:
                return TileColor.BLUE;
            case WHITE:
                return TileColor.BLACK;
            default:
                return null;
        }
    }

    @Override
    public List<Tile> getAllTiles() {
        Collection<model.factory.twinteam.Tile> tiles = factory.getTiles();
        List<Tile> ourTiles = new ArrayList<>();
        for (model.factory.twinteam.Tile tile : tiles) {
            Tile t = translateTileColor(tile.getColour());
            if (t != null) {
                ourTiles.add(t);
            }
        }
        return ourTiles;
    }

    @Override
    public List<Tile> popAllTiles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'popAllTiles'");
    }

    @Override
    public List<Tile> popTiles(TileColor tile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'popTiles'");
    }

    @Override
    public void addTiles(List<Tile> t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTiles'");
    }

    @Override
    public boolean hasTiles(TileColor type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasTiles'");
    }

}
