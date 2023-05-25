package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.layout.GridPane;
import model.TileColor;
import model.Tile;

public class DisplayPatternLine extends GridPane {
    private List<List<DisplayTile>> tiles;
    private List<TileColor> colors;

    public DisplayPatternLine() {
        colors = Arrays.asList(TileColor.values());
        clear();
    }

    public void addTile(int row, Tile tile) {
        tiles.get(row).add(new DisplayTile(tile));
    }

    public void removeTile(int row) {
        tiles.get(row).remove(0);
    }

    public void clearRow(int row) {
        tiles.set(row, new ArrayList<DisplayTile>());
    }

    public void clear() {
        tiles = new ArrayList<List<DisplayTile>>();
        for (int i = 0; i < colors.size(); i++) {
            List<DisplayTile> patternLine = new ArrayList<DisplayTile>();
            tiles.add(patternLine);
        }
    }
}
