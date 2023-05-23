package view;

public class DisplayPlayer {
    public DisplayWall wall;
    public DisplayPatternLine patternLine;
    public DisplayFloorLine floorLine;
    public int score;
    public int id;
    public String name;

    public DisplayPlayer(int id, String name) {
        wall = new DisplayWall();
        patternLine = new DisplayPatternLine();
        floorLine = new DisplayFloorLine();
        this.id = id;
        this.name = name;
        score = 0;
    }

}
