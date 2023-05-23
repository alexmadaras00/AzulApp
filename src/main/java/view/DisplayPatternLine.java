package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Color;

public class DisplayPatternLine implements Display {
    public List<List<DisplayTile>> tiles;

    public DisplayPatternLine() {
        tiles = new ArrayList<List<DisplayTile>>();
        List<Color> colors = Arrays.asList(Color.values());
        for (int i = 0; i < colors.size(); i++) {
            List<DisplayTile> patternLine = new ArrayList<DisplayTile>();
            for (int j = 0; j < i+1; j++) {
                patternLine.add(new DisplayTile(null));
            }
            tiles.add(patternLine);
        }
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
        for (List<DisplayTile> row : tiles) {
            DisplayRow patternLine = new DisplayRow(1);
            for (DisplayTile tile : row) {
                patternLine.addDisplay(tile);
            }
            column.addDisplay(patternLine);
        }
        return column.toStringList();
    }

    @Override
    public String toString() {
        return String.join("\n", toStringList());
    }

}
