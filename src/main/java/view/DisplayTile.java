package view;

import java.util.ArrayList;
import java.util.List;

import model.Color;
import model.Tile;

public class DisplayTile implements Display {
    public Tile tile;

    public DisplayTile(Tile tile) {
        this.tile = tile;
    }

    @Override
    public String toString() {
        if (tile instanceof Color) {
            return tile.toString().substring(0,1);
        }
        return "1";
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
