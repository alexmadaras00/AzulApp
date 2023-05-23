package model;

public class Player {
    private String name;
    private final PlayerBoard board = new PlayerBoard();
    private static int counter = 0;
    private final int identifier;

    public Player(){
        ++counter;
        identifier = counter;
        this.name = "Player"+identifier;
    }
    public Player(String name){
        this.name = name;
        ++counter;
        identifier = counter;
    }
    public int getIdentifier(){
        return identifier;
    }
    public String getName(){
        return name;
    }


    public void setName(String name){
        this.name=name;
    }

    public PlayerBoard getBoard() {
        return board;
    }
}
