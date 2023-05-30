package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.GridPane;
import model.Tile;
import model.TileColor;

public class DisplayWall extends GridPane {
    private List<List<TileColor>> templateWall;
    private List<List<DisplayTile>> wall;

    public DisplayWall(List<List<TileColor>> templateWall) {
        this.templateWall = templateWall;
        clear();
    }

    public void addTile(int row, Tile tile) {
        DisplayTile displayTile = new DisplayTile(tile);
        this.add(displayTile, wall.get(row).size(), row);
        wall.get(row).add(displayTile);
    }

    public void removeTile(int row, Tile tile) {
        wall.get(row).removeIf((t) -> (t.tile == tile));
        this.add(null, wall.get(row).size(), row);

    }

    public void clear() {
        wall = new ArrayList<List<DisplayTile>>();
        for (int i = 0; i < templateWall.size(); i++) {
            List<DisplayTile> wallLine = new ArrayList<DisplayTile>();
            wall.add(wallLine);
        }
        this.getChildren().clear();
    }
}
