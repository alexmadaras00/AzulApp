package model;

public class Player {
    private String name;
    private final PlayerBoard board = new PlayerBoard();
    private static int identifier = 0;

    public Player(){
        ++identifier;
    }
    public Player(String name){
        this.name = name;
        ++identifier;
    }
    public String getName(){
        return name;
    }

    public int getIdentifier(){
        return identifier;
    }
    public void setName(String name){
        this.name=name;
    }

    public PlayerBoard getBoard() {
        return board;
    }
}
