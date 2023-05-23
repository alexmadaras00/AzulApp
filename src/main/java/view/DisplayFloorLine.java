package view;

import java.util.List;

public class DisplayFloorLine implements Display {
    public List<DisplayTile> tiles;

    @Override
    public int height() {
        return 1;
    }

    @Override
    public int width() {
        return 5;
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
