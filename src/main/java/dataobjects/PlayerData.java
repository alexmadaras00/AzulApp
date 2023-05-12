package dataobjects;

import java.util.Optional;

import model.PlayerBoard;

public class PlayerData implements DataObject {
    private Optional<String> name;
    private int identifier;
    private Optional<PlayerBoard> playerBoard;
}
