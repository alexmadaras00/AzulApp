package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Tile;

public class DisplayMiddle extends VBox {
    private List<DisplayTile> tiles;

    public DisplayMiddle() {
        clear();
        this.setSpacing(3);
        this.setBackground(Background.fill(Color.FIREBRICK));
    }

    public void addTile(Tile tile) {
        DisplayTile displayTile = new DisplayTile(tile);
        tiles.add(displayTile);
        getChildren().add(displayTile);
    }

    public void removeTiles(Tile tile) {
        tiles.removeIf((t) -> (t.tile == tile));
        this.getChildren().setAll(tiles);
        // getChildren().removeIf((t) -> (((DisplayTile) t).tile == tile));
    }

    public void clear() {
        tiles = new ArrayList<DisplayTile>();
        getChildren().clear();
    }
}
