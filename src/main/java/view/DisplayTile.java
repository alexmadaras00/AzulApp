package view;

import java.util.ArrayList;
import java.util.List;

import model.Tile;

public class DisplayTile implements Display {
    public Tile tile;

    public DisplayTile(Tile tile) {
        this.tile = tile;
    }

    @Override
    public String toString() {
        return tile.toString();
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
