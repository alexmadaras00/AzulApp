package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.GridPane;
import model.Tile;

public class DisplayFactory extends GridPane {
    private List<DisplayTile> tiles;

    public int id;

    public DisplayFactory(int id) {
        this.id = id;
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
