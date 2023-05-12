package dataobjects;

import java.util.List;

import model.Tile;

public class GameState implements DataObject {
    private List<PlayerData> players;
    private List<List<Tile>> factories;
    private List<Tile> middle;
    private int currentPlayer;
}
