package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.GridPane;
import model.Tile;
import model.TileColor;

public class DisplayWall extends GridPane {
    private List<List<TileColor>> templateWall;
    private List<List<Tile>> wall;

    public DisplayWall(List<List<TileColor>> templateWall) {
        this.templateWall = templateWall;
        clear();
    }

    public void addTile(int row, Tile tile) {
        wall.get(row).add(tile);
    }

    public void removeTile(int row, Tile tile) {
        wall.get(row).removeIf((t) -> (t == tile));
    }

    public void clear() {
        wall = new ArrayList<List<Tile>>();
        for (int i = 0; i < templateWall.size(); i++) {
            List<Tile> wallLine = new ArrayList<Tile>();
            wall.add(wallLine);
        }
    }
}
