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
    
    private TileColour translateTwinTileColour(TileColor color) {
        switch (color) {
            case RED:
                return TileColour.RED;
            case CYAN:
                return TileColour.GREEN;
            case YELLOW:
                return TileColour.YELLOW;
            case BLUE:
                return TileColour.BLUE;
            case BLACK:
                return TileColour.WHITE;
            default:
                return null;
        }
    }

    @Override
    public List<TileColor> getAllTiles() {
        Collection<model.factory.twinteam.Tile> twinTiles = factory.getTiles();
        List<TileColor> ourTiles = new ArrayList<>();
        for (model.factory.twinteam.Tile twinTile : twinTiles) {
            Tile ourTile = translateTileColor(twinTile.getColour());
            if (ourTile != null) {
                ourTiles.add(ourTile);
            }
        }
        return ourTiles;
    }

    @Override
    public List<TileColor> popAllTiles() {
        List<TileColor> tiles = this.getAllTiles();
        factory.empty();
        return tiles;
    }

    @Override
    public List<TileColor> popTiles(TileColor tile) {
        Collection<model.factory.twinteam.Tile> twinTiles = factory.grab(translateTwinTileColour(tile));
        List<TileColor> ourTiles = new ArrayList<>();
        for (model.factory.twinteam.Tile twinTile : twinTiles) {
            Tile ourTile = translateTileColor(twinTile.getColour());
            if (ourTile != null) {
                ourTiles.add(ourTile);
            }
        }
        return ourTiles;
    }

    @Override
    public void addTiles(List<TileColor> ourTiles) {
        Collection<model.factory.twinteam.Tile> twinTiles = new ArrayList<>();
        for (Tile ourTile : ourTiles) {
            twinTiles.add(Tile(translateTwinTileColour(ourTile)))
        }
    }

    @Override
    public boolean hasTiles(TileColor type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasTiles'");
    }

}
