package view;

import java.util.ArrayList;
import java.util.List;

public class DisplayFactory implements Display {
    public List<DisplayTile> tiles;

    public DisplayFactory() {
        tiles = new ArrayList<DisplayTile>();
        for (int i= 0; i < width() * height(); i++) {
            tiles.add(new DisplayTile(null));
        }
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
        DisplayRow row = new DisplayRow(height());
        for (int i = 0; i < width(); i++) {
            List<DisplayTile> tileSubset = tiles.subList(i * height(), (i + 1) * height());
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
