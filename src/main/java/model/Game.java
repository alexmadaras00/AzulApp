package model;

import java.util.List;

public class Game {
    private List<Player> players;
    private Boolean isPlaying;
    private Integer round;
    private List<Tile> box;
    private List<Factory> factories;
    private List<Player> turnOrder;
    private Bag bag;
    private Middle middle;

    void startGame(List<String> playerNames){

    }
    void terminateGame(){

    }
    boolean isMoveValid(){
        return false;
    }
    void performMove(){

    }
    int getState(){
        return 0;
    }
    private void handleRound(){

    }
    private void handleEnd(){

    }
}
