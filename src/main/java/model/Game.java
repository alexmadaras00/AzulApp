package model;

import controller.Controller;
import dataobjects.GameState;
import dataobjects.GameUpdate;
import dataobjects.PlayerBoardState;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements Model {
    private List<Player> players;
    private Boolean isPlaying;
    private int round;
    private List<Tile> box;
    private List<Factory> factories;
    private GamePhase gamePhase;
    private List<Player> turnOrder;
    private Bag bag;
    private Controller controller;
    private Middle middle;

    public Game(List<Player> players) {
        this.players = players;
        this.factories = new ArrayList<>();
        this.gamePhase = GamePhase.INITIALIZED;
        this.middle = new Middle();
        this.bag = new Bag();
        this.isPlaying = false;
        this.box = new ArrayList<>();
        this.round = 0;
        this.turnOrder = players;
    }

    public List<Tile> getBox() {
        return box;
    }

    public List<Player> getTurnOrder() {
        return turnOrder;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Middle getMiddle() {
        return middle;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public Bag getBag() {
        return bag;
    }

    public int getRound() {
        return round;
    }

    public Boolean isPlaying() {
        return isPlaying;
    }

    public void startGame() {
        Collections.shuffle(turnOrder);
        isPlaying = true;
        gamePhase = GamePhase.PREPARING_ROUND;
        for (int i = 0; i < 20; i++) {
            List<Tile> colorTiles = new ArrayList<>();
            colorTiles.add(Color.RED);
            colorTiles.add(Color.BLACK);
            colorTiles.add(Color.BLUE);
            colorTiles.add(Color.CYAN);
            colorTiles.add(Color.YELLOW);
            bag.addTiles(colorTiles);
        }
        if (players.size() == 2) {
            for (int i = 0; i < 5; i++)
                factories.add(new Factory());
        } else if (players.size() == 3) {
            for (int i = 0; i < 7; i++)
                factories.add(new Factory());
        } else {
            for (int i = 0; i < 9; i++)
                factories.add(new Factory());
        }
    }

    public void terminateGame() {
        gamePhase = GamePhase.TERMINATED;
        isPlaying = false;

    }
    public boolean isCurrentPlayer(int identifier){
        for(Player p: players){
            if(p.getIdentifier()==identifier)
                return true;
        }
        return false;
    }

    public boolean isMoveValid(JSONObject move) {
        return false;
    }

    public GameUpdate performMove(JSONObject move) {
        return new GameUpdate();
    }

    public GameState toObject() {
       GameState object =  new GameState();
       List<List<Tile>> factoryTiles = new ArrayList<>();
       factories.forEach(factory -> {
           factoryTiles.add(factory.getAllTiles());
       });
       List<PlayerBoardState> playerBoards = new ArrayList<>();
       object.setPlayerBoards(playerBoards);
       object.setFactories(factoryTiles);
       object.setMiddle(middle.getAllTiles());
        return object;
    }

}
