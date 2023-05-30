package view;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.HBox;
import model.Tile;

public class DisplayFloorLine extends HBox {
    private List<DisplayTile> tiles;

    public DisplayFloorLine() {
        clear();
    }

    public void addTile(Tile tile) {
        DisplayTile displayTile = new DisplayTile(tile);
        tiles.add(displayTile);
        this.getChildren().add(displayTile);
    }

    public void removeTiles(Tile tile) {
        tiles.removeIf((t) -> (t.tile == tile));
        getChildren().removeIf((t) -> (((DisplayTile) t).tile == tile));

    }

    public void clear() {
        tiles = new LinkedList<DisplayTile>();
        this.getChildren().clear();
    }
}
