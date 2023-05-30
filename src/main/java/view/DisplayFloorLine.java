package view;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.Tile;

public class DisplayFloorLine extends HBox {
    private List<DisplayTile> tiles;

    public DisplayFloorLine() {
        clear();
        this.setBackground(Background.fill(Color.CHARTREUSE));

    }

    public void addTile(Tile tile) {
        DisplayTile displayTile = new DisplayTile(tile);
        tiles.add(displayTile);

        this.getChildren().setAll(tiles);
    }

    public void removeTiles(Tile tile) {
        tiles.removeIf((t) -> (t.tile == tile));
        this.getChildren().setAll(tiles);

    }

    public void clear() {
        tiles = new LinkedList<DisplayTile>();
        this.getChildren().clear();
    }
}
