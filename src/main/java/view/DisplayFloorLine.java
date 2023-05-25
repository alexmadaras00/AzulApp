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
        tiles.add(new DisplayTile(tile));
    }

    public void removeTiles(Tile tile) {
        tiles.removeIf((t) -> (t.tile == tile));
    }

    public void clear() {
        tiles = new LinkedList<DisplayTile>();
    }
}
