package view;

import java.util.ArrayList;
import java.util.List;

public class DisplayMiddle implements Display {
    public List<DisplayTile> tiles;

    public DisplayMiddle() {
        tiles = new ArrayList<DisplayTile>();
        for (int i = 0; i < width() * height(); i++) {
            tiles.add(new DisplayTile(null));
        }
    }

    @Override
    public int height() {
        return 2;
    }

    @Override
    public int width() {
        return 0;
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
