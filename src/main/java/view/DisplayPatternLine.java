package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Color;
import model.Tile;

public class DisplayPatternLine implements Display {
    private List<List<DisplayTile>> tiles;
    private List<Color> colors;

    public DisplayPatternLine() {
        colors = Arrays.asList(Color.values());
        tiles = new ArrayList<List<DisplayTile>>();
        for (int i = 0; i < colors.size(); i++) {
            List<DisplayTile> patternLine = new ArrayList<DisplayTile>();
            tiles.add(patternLine);
        }
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
        List<List<DisplayTile>> newTiles = new ArrayList<List<DisplayTile>>(tiles);
        for (int i = 0; i < colors.size(); i++) {
            Tile type = newTiles.get(i).isEmpty() ? null : newTiles.get(i).get(0).tile;
            for (int j = tiles.get(i).size(); j < i + 1; j++) {
                newTiles.get(i).add(new DisplayTile(type, false));
            }
        }

        DisplayColumn column = new DisplayColumn();
        for (List<DisplayTile> row : newTiles) {
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
