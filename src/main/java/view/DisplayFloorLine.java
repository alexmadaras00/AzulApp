package view;

import java.util.LinkedList;
import java.util.List;

import model.Tile;

public class DisplayFloorLine implements Display {
    private List<DisplayTile> tiles;

    public DisplayFloorLine() {
        tiles = new LinkedList<DisplayTile>();
    }

    public void addTile(Tile tile) {
        tiles.add(new DisplayTile(tile));
    }

    public void removeTiles(Tile tile) {
        tiles.removeIf((t) -> (t.tile == tile));
    }

    @Override
    public int height() {
        return 1;
    }

    @Override
    public int width() {
        return 7;
    }

    @Override
    public String toString() {
        return String.join("\n", toStringList());
    }

    @Override
    public List<String> toStringList() {
        DisplayRow block = new DisplayRow(1);
        for (DisplayTile tile : tiles) {
            block.addDisplay(tile);
        }
        return block.toStringList();
    }
}
