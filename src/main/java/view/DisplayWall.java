package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Color;

public class DisplayWall implements Display {
    public List<List<DisplayTile>> wall;

    public DisplayWall() {
        wall = new ArrayList<List<DisplayTile>>();
        List<Color> colors = Arrays.asList(Color.values());
        for (int i = 0; i < colors.size(); i++) {
            List<DisplayTile> wallLine = new ArrayList<DisplayTile>();
            for (int j = 0; j < colors.size(); j++) {
                Color color = colors.get((j - i + colors.size()) % colors.size());
                wallLine.add(new DisplayTile(color, true));
            }
            wall.add(wallLine);
        }
    }

    public int getIndexOfTile(int row, DisplayTile tile) {
        List<DisplayTile> wallLine = wall.get(row);
        for (int i = 0; i < wallLine.size(); i++) {
            if (wallLine.get(i).tile == tile.tile) {
                return i;
            }
        }
        return -1;
    }

    public void setTile(int row, DisplayTile tile, boolean isPlaceholder) {
        int index = getIndexOfTile(row, tile);
        wall.get(row).get(index).isPlaceholder = isPlaceholder;
    }

    @Override
    public int height() {
        return 5;
    }

    @Override
    public int width() {
        return 5;
    }

    @Override
    public List<String> toStringList() {
        DisplayColumn column = new DisplayColumn();
        for (List<DisplayTile> row : wall) {
            DisplayRow wallLine = new DisplayRow(1);
            for (DisplayTile tile : row) {
                wallLine.addDisplay(tile);
            }
            column.addDisplay(wallLine);
        }
        return column.toStringList();
    }

    @Override
    public String toString() {
        return String.join("\n", toStringList());
    }
}
