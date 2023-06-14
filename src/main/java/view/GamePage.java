package view;

import java.lang.reflect.Field;
import java.util.List;

import controller.ControllerImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.ModelProxy;
import model.Player;
import model.PlayerTile;
import model.Tile;
import model.TileColor;

public class GamePage {
    private ModelProxy model;

    private ControllerImpl controllerImpl;

    private View view;

    public void setView(View view) {
        this.view = view;
    }

    public void setModel(ModelProxy model) {
        this.model = model;
    }

    public void setController(ControllerImpl controllerImpl) {
        this.controllerImpl = controllerImpl;
    }

    @FXML
    private Button buttonF1T1;
    @FXML
    private Button buttonF1T2;
    @FXML
    private Button buttonF1T3;
    @FXML
    private Button buttonF1T4;
    @FXML
    private Button buttonF2T1;
    @FXML
    private Button buttonF2T2;
    @FXML
    private Button buttonF2T3;
    @FXML
    private Button buttonF2T4;
    @FXML
    private Button buttonF3T1;
    @FXML
    private Button buttonF3T2;
    @FXML
    private Button buttonF3T3;
    @FXML
    private Button buttonF3T4;
    @FXML
    private Button buttonF4T1;
    @FXML
    private Button buttonF4T2;
    @FXML
    private Button buttonF4T3;
    @FXML
    private Button buttonF4T4;
    @FXML
    private Button buttonF5T1;
    @FXML
    private Button buttonF5T2;
    @FXML
    private Button buttonF5T3;
    @FXML
    private Button buttonF5T4;
    @FXML
    private Button buttonF6T1;
    @FXML
    private Button buttonF6T2;
    @FXML
    private Button buttonF6T3;
    @FXML
    private Button buttonF6T4;
    @FXML
    private Button buttonF7T1;
    @FXML
    private Button buttonF7T2;
    @FXML
    private Button buttonF7T3;
    @FXML
    private Button buttonF7T4;
    @FXML
    private Button buttonF8T1;
    @FXML
    private Button buttonF8T2;
    @FXML
    private Button buttonF8T3;
    @FXML
    private Button buttonF8T4;
    @FXML
    private Button buttonF9T1;
    @FXML
    private Button buttonF9T2;
    @FXML
    private Button buttonF9T3;
    @FXML
    private Button buttonF9T4;
    @FXML
    private GridPane factory1;
    @FXML
    private GridPane factory2;
    @FXML
    private GridPane factory3;
    @FXML
    private GridPane factory4;
    @FXML
    private GridPane factory5;
    @FXML
    private GridPane factory6;
    @FXML
    private GridPane factory7;
    @FXML
    private GridPane factory8;
    @FXML
    private GridPane factory9;
    @FXML
    private VBox middle;
    @FXML
    private HBox player1Floor;
    @FXML
    private Label player1Name;
    @FXML
    private GridPane player1PL1;
    @FXML
    private GridPane player1PL2;
    @FXML
    private GridPane player1PL3;
    @FXML
    private GridPane player1PL4;
    @FXML
    private GridPane player1PL5;
    @FXML
    private Label player1Score;
    @FXML
    private GridPane player1W1;
    @FXML
    private GridPane player1W2;
    @FXML
    private GridPane player1W3;
    @FXML
    private GridPane player1W4;
    @FXML
    private GridPane player1W5;
    @FXML
    private HBox player2Floor;
    @FXML
    private Label player2Name;
    @FXML
    private GridPane player2PL1;
    @FXML
    private GridPane player2PL2;
    @FXML
    private GridPane player2PL3;
    @FXML
    private GridPane player2PL4;
    @FXML
    private GridPane player2PL5;
    @FXML
    private Label player2Score;
    @FXML
    private GridPane player2W1;
    @FXML
    private GridPane player2W2;
    @FXML
    private GridPane player2W3;
    @FXML
    private GridPane player2W4;
    @FXML
    private GridPane player2W5;
    @FXML
    private HBox player3Floor;
    @FXML
    private Label player3Name;
    @FXML
    private GridPane player3PL1;
    @FXML
    private GridPane player3PL2;
    @FXML
    private GridPane player3PL3;
    @FXML
    private GridPane player3PL4;
    @FXML
    private GridPane player3PL5;
    @FXML
    private Label player3Score;
    @FXML
    private GridPane player3W1;
    @FXML
    private GridPane player3W2;
    @FXML
    private GridPane player3W3;
    @FXML
    private GridPane player3W4;
    @FXML
    private GridPane player3W5;
    @FXML
    private HBox player4Floor;
    @FXML
    private Label player4Name;
    @FXML
    private GridPane player4PL1;
    @FXML
    private GridPane player4PL2;
    @FXML
    private GridPane player4PL3;
    @FXML
    private GridPane player4PL4;
    @FXML
    private GridPane player4PL5;
    @FXML
    private Label player4Score;
    @FXML
    private GridPane player4W1;
    @FXML
    private GridPane player4W2;
    @FXML
    private GridPane player4W3;
    @FXML
    private GridPane player4W4;
    @FXML
    private GridPane player4W5;
    @FXML
    private VBox playerboard1;
    @FXML
    private VBox playerboard2;
    @FXML
    private VBox playerboard3;
    @FXML
    private VBox playerboard4;

    Location from;
    int fromIndex;
    int ToIndex;
    Location to;
    int playerId;
    TileColor color;



    @FXML
    void selectTile(ActionEvent event) {
        Button button = (Button) event.getSource();
        Location buttonLocation = Location.MIDDLE;
        int buttonLocationIndex = 0;
        String buttonLocationId = button.getParent().getId();
        if (buttonLocationId.contains("factory")) {
            buttonLocation = Factory.MIDDLE;
            buttonLocationIndex = Integer.parseInt(buttonLocationId.substring(6,7));
        }
        
        if (from == null || (from == buttonLocation && fromIndex == buttonLocationIndex)) {
            from = buttonLocation;
            fromIndex = buttonLocationIndex;
            color = getBackwardsColor(button);
        } else {
            from = null;
            fromIndex = 0;
            color = null;
        }
    }

    private TileColor getBackwardsColor(Button button) {
        Paint background = button.getBackground().getFills().get(0).getFill();
        if (background == Color.RED) return TileColor.RED;
        if (background == Color.BLACK) return TileColor.BLACK;
        if (background == Color.BLUE) return TileColor.BLUE;
        if (background == Color.YELLOW) return TileColor.YELLOW;
        if (background == Color.CYAN) return TileColor.CYAN;
        return null;
    }

    @FXML
    void selectToLocation(ActionEvent event) {
        toast("TO SELECT");

    }

    public void toast(String msg) {
        System.out.println(msg);
    }

    private void setTileColor(Button tileButton, Tile tile) {
        tileButton.setBackground(Background.fill(getColor(tile)));
    }

    private Paint getColor(Tile tile) {
        if (tile == null) {
            return Color.rgb(0, 0, 0, 0);
        }
        if (!(tile instanceof TileColor)) {
            return Color.ANTIQUEWHITE;
        }
        switch ((TileColor) tile) {
            case RED:
                return Color.RED;
            case BLACK:
                return Color.BLACK;

            case BLUE:
                return Color.BLUE;

            case YELLOW:
                return Color.YELLOW;

            case CYAN:
                return Color.CYAN;

            default:
                return null;
        }
    }

    private void updateFactories() {

        if (model.getFactoryCount() <= 7) {
            if (model.getFactoryCount() <= 5) {
                factory7.setVisible(false);
                factory6.setVisible(false);
            }
            factory9.setVisible(false);
            factory8.setVisible(false);
        }

        for (int i = 0; i < model.getFactoryCount(); i++) {
            Tile[] factoryTiles = model.getFactory(i);
            for (int j = 0; j < 4; j++) {
                setTileColor(getElementByName("buttonF" + (i + 1) + "T" + (j + 1), Button.class), factoryTiles[j]);
            }
        }
    }

    private class TileButton extends Button {

        TileButton(Tile tile) {
            super();
            this.setBackground(Background.fill(getColor(tile)));
            if (tile instanceof PlayerTile) {
                this.setText("1");
            }
            this.setPrefHeight(30.0);
            this.setPrefWidth(30.0);

            // TODO more button configs to make it similar as other buttons;
        }

    }

    private void updateMiddle() {
        middle.getChildren().clear();
        List<Tile> tiles = model.getMiddle();
        for (Tile tile : tiles) {
            middle.getChildren().add(new TileButton(tile));
        }
    }

    private void updatePlayers() {
        List<Player> players = model.getPlayerList();
        switch (players.size()) {
            case 2:
                playerboard3.setVisible(false);
            case 3:
                playerboard4.setVisible(false);
            default:
                break;
        }
        for (int i = 0; i < players.size(); i++) {
            updatePlayer(players.get(i), i + 1);
        }
    }

    private void updatePlayer(Player player, int place) {
        updateWall(player, place);
        updatePatternLine(player, place);
        updateFloorLine(player, place);
        updateName(player, place);
        updateScore(player, place);
    }

    private void updateScore(Player player, int place) {
        getElementByName("player" + place + "Score", Label.class).setText("Score: " + player.getBoard().getScore());
    }

    private void updateName(Player player, int place) {
        getElementByName("player" + place + "Name", Label.class).setText(player.getName());
    }

    private void setFloorLine(HBox floorLine, List<Tile> tiles) {
        floorLine.getChildren().clear();
        for (Tile tile : tiles) {
            floorLine.getChildren().add(new TileButton(tile));
        }
    }

    private void updateFloorLine(Player player, int place) {
        List<Tile> floorTiles = player.getBoard().getFloorLine().getCopyTiles();
        setFloorLine(getElementByName("player" + place + "Floor", HBox.class), floorTiles);
    }

    private void updatePatternLine(Player player, int place) {
        List<List<Tile>> floorTiles = player.getBoard().getPatternLine().getCopyTable();
        for (int line = 1; line <= 5; line++) {
            setPatternLine(getElementByName("player" + place + "PL" + line, GridPane.class), floorTiles.get(line - 1));
        }

    }

    private void setPatternLine(GridPane patternLine, List<Tile> tiles) {
        patternLine.getChildren().clear();
        for (int i = 0; i < tiles.size(); i++) {
            patternLine.add(new TileButton(tiles.get(i)), 4 - i, 0);
        }

    }

    private void updateWall(Player player, int place) {
        List<List<Tile>> wallTiles = player.getBoard().getWall().getCopyTable();
        for (int line = 1; line <= 5; line++) {
            setWallLine(getElementByName("player" + place + "W" + line, GridPane.class), wallTiles.get(line - 1));
        }
    }

    private void setWallLine(GridPane wallLine, List<Tile> tiles) {
        wallLine.getChildren().clear();
        for (int i = 0; i < 5; i++) {
            wallLine.add(new TileButton(tiles.get(i)), i, 0);
        }
    }

    public void update() {
        updateFactories();
        updateMiddle();
        updatePlayers();
    }

    private <T> T getElementByName(String name, Class<T> clazz) {
        Field field;
        try {
            field = this.getClass().getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            // bad input
            return null;
        } catch (SecurityException e) {
            // should only look at elements in this class, so should not be triggered
            e.printStackTrace();
            return null;
        }
        try {
            return clazz.cast(field.get(this));
        } catch (IllegalArgumentException e) {
            // this should not happen, as we are giving this
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // this should not happen, as we are accessing fields inside the class
            e.printStackTrace();
        }
        return null;
    }

}
