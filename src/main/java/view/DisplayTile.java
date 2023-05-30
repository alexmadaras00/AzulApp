package view;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
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
        super();
        this.tile = tile;
        this.setBackground(Background.fill(translateColor(tile)));
        this.setBorder(Border.stroke(Color.ANTIQUEWHITE));
        this.setPrefWidth(40);
        this.setPrefHeight(40);
        this.setMinWidth(40);
        this.setMinHeight(40);
        this.setMaxWidth(40);
        this.setMaxHeight(40);
    }

}
