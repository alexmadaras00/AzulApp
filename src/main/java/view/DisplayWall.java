package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Color;
import model.Tile;

public class DisplayWall implements Display {
    private List<List<DisplayTile>> wall;

    public DisplayWall() {
        wall = new ArrayList<List<DisplayTile>>();
        List<Color> colors = Arrays.asList(Color.values());
        for (int i = 0; i < colors.size(); i++) {
            List<DisplayTile> wallLine = new ArrayList<DisplayTile>();
            for (int j = 0; j < colors.size(); j++) {
                Color color = colors.get((j - i + colors.size()) % colors.size());
                wallLine.add(new DisplayTile(color, false));
            }
            wall.add(wallLine);
        }
    }

    private int getIndexOfTile(int row, Tile tile) {
        List<DisplayTile> wallLine = wall.get(row);
        for (int i = 0; i < wallLine.size(); i++) {
            if (wallLine.get(i).tile == tile) {
                return i;
            }
        }
        return -1;
    }

    public void setTile(int row, Tile tile, boolean isFilled) {
        int index = getIndexOfTile(row, tile);
        wall.get(row).get(index).isFilled = isFilled;
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
