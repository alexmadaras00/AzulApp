package model;

import controller.Mediator;
import dataobjects.GameState;
import dataobjects.GameUpdate;
import org.json.JSONObject;

import java.util.List;

public class Game implements Model{
    private List<Player> players;
    private Boolean isPlaying;
    private int round;
    private List<Tile> box;
    private List<Factory> factories;
    private List<Player> turnOrder;
    private Bag bag;
    private Middle middle;
    public void startGame(){

    }
    public void terminateGame(){

    }
    public boolean isMoveValid(JSONObject move){
        return false;
    }
    public GameUpdate performMove(JSONObject move){
        return new GameUpdate();
    }
    public GameState getState(){
        return new GameState();
    }

    @Override
    public void connectController(Mediator IController) {

    }
}
