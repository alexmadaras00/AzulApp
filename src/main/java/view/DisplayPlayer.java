package view;

import java.util.List;

import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.TileColor;

public class DisplayPlayer extends VBox {
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
        this.setBackground(Background.fill(Color.BISQUE));
        this.setSpacing(7);
        this.getChildren().addAll(patternLine, wall, floorLine);
    }

}
