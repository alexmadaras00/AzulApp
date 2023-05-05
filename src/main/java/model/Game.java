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
}
