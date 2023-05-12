package dataobjects;

import java.util.List;
import java.util.Optional;

import model.Tile;

public class GameUpdate implements DataObject {
    private List<PlayerData> players;
    private Optional<List<List<Tile>>> factories;
    private Optional<List<Tile>> middle;
    private Move move;
    private int nextPlayer;
}
