package model.factory.twinteam;


public class Tile {

    private final TileColour colour;
    private TileCollection location;

    public Tile(TileColour colour) {
        this.colour = colour;
    }

    public TileColour getColour() {
        return this.colour;
    }

    public TileCollection getLocation() {
        return this.location;
    }

    public void setLocation(TileCollection newLocation) {
        this.location = newLocation;
    }
}
