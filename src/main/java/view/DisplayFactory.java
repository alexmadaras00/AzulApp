package view;

import java.util.ArrayList;
import java.util.List;

import model.Tile;

public class DisplayFactory implements Display {
    private List<DisplayTile> tiles;

    public DisplayFactory() {
        tiles = new ArrayList<DisplayTile>();
    }

    public void addTile(DisplayTile tile) {
        tiles.add(tile);
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
        return 2;
    }

    public List<String> toStringList() {
        List<DisplayTile> filledFactoryTiles = new ArrayList<DisplayTile>(tiles);
        for (int i = tiles.size(); i < width() * height(); i++) {
            filledFactoryTiles.add(new DisplayTile(null));
        }
        DisplayRow row = new DisplayRow(height());
        for (int i = 0; i < width(); i++) {
            List<DisplayTile> tileSubset = filledFactoryTiles.subList(i * height(), (i + 1) * height());
            DisplayColumn column = new DisplayColumn();
            for (DisplayTile tile : tileSubset) {
                column.addDisplay(tile);
            }
            row.addDisplay(column);
        }
        return row.toStringList();
    }

    @Override
    public String toString() {
        return String.join("\n", toStringList());
    }
}
