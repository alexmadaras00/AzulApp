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
        tiles.add(new DisplayTile(tile));
    }

    public void removeTiles(Tile tile) {
        tiles.removeIf((t) -> (t.tile == tile));
    }

    public void clear() {
        tiles = new ArrayList<DisplayTile>();

    }
}
