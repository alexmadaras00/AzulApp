package view;

import javafx.scene.control.Button;

import model.Tile;

public class DisplayTile extends Button {
    public Tile tile;

    public DisplayTile(Tile tile) {
        super();
        this.tile = tile;
    }

}
