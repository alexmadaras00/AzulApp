package view;

import java.util.ArrayList;
import java.util.List;

import model.Tile;

public class DisplayWall implements Display {
    private List<List<Tile>> templateWall;
    private List<List<Tile>> wall;

    public DisplayWall(List<List<Tile>> templateWall) {
        this.templateWall = templateWall;
        wall = new ArrayList<List<Tile>>();
        for (int i = 0; i < templateWall.size(); i++) {
            List<Tile> wallLine = new ArrayList<Tile>();
            wall.add(wallLine);
        }
    }

    public void addTile(int row, Tile tile) {
        wall.get(row).add(tile);
    }

    public void removeTile(int row, Tile tile) {
        wall.get(row).removeIf((t) -> (t == tile));
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
        for (int row = 0; row < wall.size(); row++) {
            DisplayRow wallLine = new DisplayRow(1);
            for (Tile tile : templateWall.get(row)) {
                wallLine.addDisplay(new DisplayTile(tile, wall.get(row).contains(tile)));
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
