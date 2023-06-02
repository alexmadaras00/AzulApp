package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Tile;
import model.TileColor;

public class DisplayWall extends GridPane {
    private List<List<TileColor>> templateWall;
    private List<List<DisplayTile>> wall;

    public DisplayWall(List<List<TileColor>> templateWall) {
        this.templateWall = templateWall;
        this.setBackground(Background.fill(Color.WHITESMOKE));
        clear();
    }

    public void addTile(int row, Tile tile) {
        DisplayTile displayTile = new DisplayTile(tile);
        this.add(displayTile, wall.get(row).size(), row);
        wall.get(row).add(displayTile);
    }

    public void removeTile(int row, Tile tile) {
        wall.get(row).removeIf((t) -> (t.tile == tile));
        this.getChildren()
                .removeIf(node -> GridPane.getColumnIndex(node) == wall.get(row).size()
                        && GridPane.getRowIndex(node) == row);

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
