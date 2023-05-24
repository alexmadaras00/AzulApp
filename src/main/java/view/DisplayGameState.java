package view;

import java.util.ArrayList;
import java.util.List;

import model.Tile;

public class DisplayGameState {

    public List<DisplayFactory> factories;
    public DisplayMiddle middle;
    public List<DisplayPlayer> players;
    public int activePlayer;
    public List<List<Tile>> wallTemplate;

    public DisplayGameState() {
        factories = new ArrayList<DisplayFactory>();
        middle = new DisplayMiddle();
        activePlayer = 0;
        players = new ArrayList<DisplayPlayer>();
    }

    private DisplayColumn blockSetup() {
        DisplayColumn column = new DisplayColumn();
        DisplayRow tableBlock = new DisplayRow(2);
        for (DisplayFactory factory : factories) {
            tableBlock.addDisplay(factory);
        }
        tableBlock.addDisplay(middle);
        column.addDisplay(tableBlock);
        DisplayRow boardBlock = new DisplayRow(5);
        boardBlock.addDisplay(players.get(activePlayer).patternLine);
        boardBlock.addDisplay(players.get(activePlayer).wall);
        column.addDisplay(boardBlock);
        DisplayRow floorLineBoard = new DisplayRow(1);
        floorLineBoard.addDisplay(players.get(activePlayer).floorLine);
        column.addDisplay(floorLineBoard);
        return column;

    }

    public String toString() {
        DisplayColumn blocks = blockSetup();
        return blocks.toString();
    }

    public DisplayPlayer getPlayer(int playerID) {
        for (DisplayPlayer player : players) {
            if (player.id == playerID) {
                return player;
            }
        }
        return null;
    }

    public void setWallPattern(List<List<Tile>> wallTemplate) {
        this.wallTemplate = wallTemplate;
    }

    public void addPlayer(int id, String name) {
        players.add(new DisplayPlayer(id, name, wallTemplate));
    }

    public void setActivePlayer(int playerID) {
        activePlayer = players.indexOf(getPlayer(playerID));
    }
}
