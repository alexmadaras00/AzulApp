package model;

public class Player {
    private String name;
    private PlayerBoard board;
    private int identifier;

    public String getName(){
        return "";
    }
    public void setName(String name){
    }

    public PlayerBoard getBoard() {
        return new PlayerBoard();
    }
}
