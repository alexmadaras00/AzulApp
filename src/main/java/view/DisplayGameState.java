package view;

import java.util.ArrayList;
import java.util.List;

public class DisplayGameState {

    public List<DisplayFactory> factories;
    public DisplayMiddle middle;
    public List<DisplayPlayer> players;
    public int activePlayer;


    
    public DisplayGameState() {
        factories = new ArrayList<DisplayFactory>();
        factories.add(new DisplayFactory());
        middle = new DisplayMiddle();
        activePlayer = 0;
        players = new ArrayList<DisplayPlayer>();
        players.add(new DisplayPlayer(activePlayer, "Bob"));
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
}
