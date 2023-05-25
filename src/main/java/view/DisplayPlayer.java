package view;

import java.util.List;

import javafx.scene.Group;
import model.TileColor;

public class DisplayPlayer extends Group {
    public DisplayWall wall;
    public DisplayPatternLine patternLine;
    public DisplayFloorLine floorLine;
    public int score;
    public int id;
    public String name;

    public DisplayPlayer(int id, String name, List<List<TileColor>> wallTemplate) {
        wall = new DisplayWall(wallTemplate);
        patternLine = new DisplayPatternLine();
        floorLine = new DisplayFloorLine();
        this.id = id;
        this.name = name;
        score = 0;
    }

}
