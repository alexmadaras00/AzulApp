package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.TileColor;
import model.Tile;

public class DisplayPatternLine extends GridPane {
    private List<List<DisplayTile>> tiles;
    private List<TileColor> colors;

    public DisplayPatternLine() {
        colors = Arrays.asList(TileColor.values());
        clear();
        this.setBackground(Background.fill(Color.CHOCOLATE));

    }

    public void addTile(int row, Tile tile) {
        DisplayTile displayTile = new DisplayTile(tile);
        this.add(displayTile, tiles.get(row).size(), row);
        tiles.get(row).add(displayTile);
    }

    public void removeTile(int row) {
        tiles.get(row).remove(0);
        this.add(null, tiles.get(row).size(), row);
    }

    public void clearRow(int row) {
        for (DisplayTile displayTile : tiles.get(row)) {
            this.getChildren().remove(displayTile);
        }
        tiles.set(row, new ArrayList<DisplayTile>());
    }

    public void clear() {
        tiles = new ArrayList<List<DisplayTile>>();
        for (int i = 0; i < colors.size(); i++) {
            List<DisplayTile> patternLine = new ArrayList<DisplayTile>();
            tiles.add(patternLine);
        }
        this.getChildren().clear();
    }
}
