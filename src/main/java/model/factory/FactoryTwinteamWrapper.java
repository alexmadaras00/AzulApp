package model.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.factory.twinteam.CollectionOverCapacityException;
import model.factory.twinteam.FactoryDisplay;
import model.factory.twinteam.TileColour;
import shared.TileColor;

public class FactoryTwinteamWrapper implements FactoryInterface {
    private FactoryDisplay factory;

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
        return convertToOwnTiles(twinTiles);
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
        return convertToOwnTiles(twinTiles);
    }

    @Override
    public void addTiles(List<TileColor> ourTiles) {
        Collection<model.factory.twinteam.Tile> twinTiles = new ArrayList<>();
        for (TileColor ourTile : ourTiles) {
            twinTiles.add(new model.factory.twinteam.Tile(translateTwinTileColour(ourTile)));
        }
        try {
            factory.addTiles(twinTiles);
        } catch (CollectionOverCapacityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasTiles(TileColor type) {
        List<TileColor> tilesInFactory = this.getAllTiles();
        return tilesInFactory.contains(type);
    }

    private List<TileColor> convertToOwnTiles(Collection<model.factory.twinteam.Tile> twinTiles) {
        List<TileColor> ourTiles = new ArrayList<>();
        for (model.factory.twinteam.Tile twinTile : twinTiles) {
            TileColor ourTile = translateTileColor(twinTile.getColour());
            if (ourTile != null) {
                ourTiles.add(ourTile);
            }
        }
        return ourTiles;
    }

}
