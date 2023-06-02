package view;

import java.util.ArrayList;
import java.util.List;

import model.TileColor;
import model.PlayerTile;
import model.Tile;

public class DisplayTile implements Display {
    public Tile tile;
    public boolean isFilled;

    public DisplayTile(Tile tile) {
        this.tile = tile;
        isFilled = true;
    }

    public DisplayTile(Tile tile, boolean isFilled) {
        this.tile = tile;
        this.isFilled = isFilled;
    }

    @Override
    public String toString() {
        if (tile instanceof TileColor) {
            String character = tile.toString().substring(0, 1);
            if (!isFilled) {
                character = character.toLowerCase();
            }
            return character;
        } else if (tile instanceof PlayerTile) {
            return "1";
        }
        return "_";
    }

    @Override
    public int height() {
        return 1;
    }

    @Override
    public int width() {
        return 1;
    }

    @Override
    public List<String> toStringList() {
        List<String> stringList = new ArrayList<String>();
        stringList.add(toString());
        return stringList;
    }

}
