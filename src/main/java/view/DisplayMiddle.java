package view;

import java.util.ArrayList;
import java.util.List;

import model.Tile;

public class DisplayMiddle implements Display {
    private List<DisplayTile> tiles;

    public DisplayMiddle() {
        tiles = new ArrayList<DisplayTile>();
    }

    public void addTile(Tile tile) {
        tiles.add(new DisplayTile(tile));
    }

    public void removeTiles(Tile tile) {
        tiles.removeIf((t) -> (t.tile == tile));
    }

    @Override
    public int height() {
        return 2;
    }

    @Override
    public int width() {
        return -1;
    }

    public List<String> toStringList() {
        DisplayColumn col = new DisplayColumn();
        for (int i = 0; i < height(); i++) {
            DisplayRow row = new DisplayRow(1);
            for (int j = 0; j < tiles.size(); j++) {
                if (j % height() == i) {
                    row.addDisplay(tiles.get(j));
                }
            }
            col.addDisplay(row);
        }
        return col.toStringList();
    }

    @Override
    public String toString() {
        return String.join("\n", toStringList());
    }
}
