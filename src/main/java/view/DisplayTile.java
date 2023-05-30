package view;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import model.Tile;
import model.TileColor;

public class DisplayTile extends Button {
    public Tile tile;

    private Color translateColor(Tile tile) {
        if (tile == TileColor.BLACK) {
            return Color.BLACK;
        } else if (tile == TileColor.CYAN) {
            return Color.CYAN;
        } else if (tile == TileColor.BLUE) {
            return Color.BLUE;
        } else if (tile == TileColor.RED) {
            return Color.RED;
        } else if (tile == TileColor.YELLOW) {
            return Color.YELLOW;
        }
        return null; 
    }

    public DisplayTile(Tile tile) {
        super(tile.toString());
        this.tile = tile;
        this.setBackground(Background.fill(translateColor(tile)));
    }

}
