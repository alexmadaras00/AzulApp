package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.VBox;
import model.Tile;

public class DisplayMiddle extends VBox {
    private List<DisplayTile> tiles;

    public DisplayMiddle() {
        clear();
    }

    public void addTile(Tile tile) {
        DisplayTile displayTile = new DisplayTile(tile);
        tiles.add(displayTile);
        getChildren().add(displayTile);
    }

    public void removeTiles(Tile tile) {
        tiles.removeIf((t) -> (t.tile == tile));
        getChildren().removeIf((t) -> (((DisplayTile) t).tile == tile));
    }

    public void clear() {
        tiles = new ArrayList<DisplayTile>();
        getChildren().clear();
    }
}
