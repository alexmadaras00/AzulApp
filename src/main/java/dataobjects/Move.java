package dataobjects;

import java.util.OptionalInt;

import model.Color;

public class Move implements DataObject {
    private Boolean isFactory;
    private OptionalInt number;
    private Color type;
    private PlayerData player;
}
