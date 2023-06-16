package view;

import java.lang.reflect.Field;
import java.util.List;

import controller.Controller;
import controller.ModelProxy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import model.Player;
import model.PlayerBoard;
import shared.Location;
import shared.PlayerTile;
import shared.Tile;
import shared.TileColor;

public class GamePage implements View {
    private ModelProxy model;

    private Controller controller;

    public void setModel(ModelProxy model) {
        this.model = model;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
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

    private Pair<Location, Integer> from;
    private Pair<Location, Integer> to;
    private int playerId;
    private TileColor color;
    private String selectedId;

    private void clearSelection() {
        from = new Pair<>(null, 0);
        to = new Pair<>(null, 0);
        playerId = 0;
        color = null;
        selectedId = null;

        selectTiles(from, color);
    }

    private Border selectionBorder = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
            null,
            new BorderWidths(2)));

    private void selectTiles(Pair<Location, Integer> from, TileColor color) {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 5; j++) {
                Button button = getElementByName("buttonF" + i + "T" + j, Button.class);
                Border border = Border.EMPTY;
                if (from.getKey() == Location.FACTORY && from.getValue() == i - 1 && getBackwardsColor(button) == color) {
                    border = selectionBorder;
                }
                button.setBorder(border);
            }
        }
        for (Node node : middle.getChildren()) {
            Button button = (Button) node;
            Border border = Border.EMPTY;
            if (from.getKey() == Location.MIDDLE && getBackwardsColor(button) == color) {
                border = selectionBorder;
            }
            button.setBorder(border);
        }

    }

    @FXML
    void selectToLocation(MouseEvent event) {
        if (selectedId != null) {
            String clickedId = ((Node) event.getSource()).getId();
            playerId = model.getPlayerList().get(Integer.parseInt(clickedId.substring(6, 7)) - 1).getIdentifier();
            to = new Pair<>(Location.FLOOR_LINE,0);
            if (clickedId.contains("PL")) {
                to = new Pair<>(Location.PATTERN_LINE, Integer.parseInt(clickedId.substring(9, 10)) - 1);
            }
            toast("perform move " + color + " from " + from.getKey() + " " + from.getValue() + " to " + to.getKey() + " "
                    + to.getValue()
                    + " of player " + playerId);

            controller.performMove(from, to, playerId, color);
            clearSelection();
        }
    }

    @FXML
    void selectTile(ActionEvent event) {
        Button button = (Button) event.getSource();
        Location buttonLocation = Location.MIDDLE;
        int buttonLocationIndex = 0;
        TileColor buttonColor = getBackwardsColor(button);

        String buttonId = button.getId();
        buttonId = buttonId == null ? "buttonMiddle" + buttonColor : buttonId;
        if (buttonId.contains("buttonF")) {
            buttonLocation = Location.FACTORY;
            buttonLocationIndex = Integer.parseInt(buttonId.substring(7, 8)) - 1;
        }

        if (selectedId == null || (selectedId != buttonId)) {
            selectedId = buttonId;
            from = new Pair<>(buttonLocation, buttonLocationIndex);
            color = buttonColor;
            toast("selected " + color + " from " + from.getKey() + " " + from.getValue());
            selectTiles(from, color);
        } else {
            toast("unselected " + color + " from " + from.getKey() + " " + from.getValue());
            clearSelection();
        }
    }

    private TileColor getBackwardsColor(Button button) {
        Paint background = button.getBackground().getFills().get(0).getFill();
        if (background == Color.RED)
            return TileColor.RED;
        if (background == Color.BLACK)
            return TileColor.BLACK;
        if (background == Color.BLUE)
            return TileColor.BLUE;
        if (background == Color.YELLOW)
            return TileColor.YELLOW;
        if (background == Color.CYAN)
            return TileColor.CYAN;
        return null;
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
            case RED -> {return Color.RED;}
            case BLACK -> {return Color.BLACK;}
            case BLUE -> {return Color.BLUE;}
            case YELLOW -> {return Color.YELLOW;}
            case CYAN -> {return Color.CYAN;}
        }
        return null;
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

    private class MiddleButton extends TileButton {

        MiddleButton(Tile tile) {
            super(tile);
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    selectTile(event);
                }
            });
        }

    }

    private void updateMiddle() {
        middle.getChildren().clear();
        List<Tile> tiles = model.getMiddle();
        for (Tile tile : tiles) {
            middle.getChildren().add(new MiddleButton(tile));
        }
    }

    private void updatePlayers() {
        List<PlayerBoard> players = model.getPlayerBoardList();
        switch (players.size()) {
            case 2:
                playerboard3.setVisible(false);
            case 3:
                playerboard4.setVisible(false);
            default:
                break;
        }
        for (int i = 0; i < players.size(); i++) {
            Border border = Border.EMPTY;
            if (model.getCurrentPlayer() == players.get(i).getPlayerIdentifier()) {
                border = selectionBorder;
            }
            getElementByName("playerboard" + (i + 1), VBox.class).setBorder(border);
            updatePlayer(players.get(i), i + 1);
        }
    }

    private void updatePlayer(PlayerBoard player, int place) {
        updateWall(player, place);
        updatePatternLine(player, place);
        updateFloorLine(player, place);
        updateName(player, place);
        updateScore(player, place);
    }

    private void updateScore(PlayerBoard player, int place) {
        getElementByName("player" + place + "Score", Label.class).setText("Score: " + player.getScore());
    }

    private void updateName(PlayerBoard player, int place) {
        getElementByName("player" + place + "Name", Label.class).setText(player.getPlayerName());
    }

    private void setFloorLine(HBox floorLine, List<Tile> tiles) {
        floorLine.getChildren().clear();
        for (Tile tile : tiles) {
            floorLine.getChildren().add(new TileButton(tile));
        }
    }

    private void updateFloorLine(PlayerBoard player, int place) {
        List<Tile> floorTiles = player.getFloorLine().getCopyTiles();
        setFloorLine(getElementByName("player" + place + "Floor", HBox.class), floorTiles);
    }

    private void updatePatternLine(PlayerBoard player, int place) {
        List<List<Tile>> floorTiles = player.getPatternLine().getCopyTable();
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

    private void updateWall(PlayerBoard player, int place) {
        List<List<Tile>> wallTiles = player.getWall().getCopyTable();
        for (int line = 1; line <= 5; line++) {
            setWallLine(getElementByName("player" + place + "W" + line, GridPane.class), wallTiles.get(line - 1));
        }
    }

    private void setWallLine(GridPane wallLine, List<Tile> tiles) {
        wallLine.getChildren().clear();
        List<Color> pattern = List.of(Color.YELLOW, Color.RED, Color.BLACK, Color.CYAN, Color.BLUE);
        for (int i = 0; i < 5; i++) {
            TileButton button = new TileButton(tiles.get(i));
            if (tiles.get(i) == null) {
                Button holderButton = new Button();

                int row = Integer.parseInt(wallLine.getId().substring(8));
                Color color = pattern.get((i - row + 5) % 5);
                holderButton.setBackground(Background.fill(Color.rgb((int) (color.getRed() * 255),
                        (int) (color.getGreen() * 255), (int) (color.getBlue() * 255), 0.25)));
                holderButton.setPrefHeight(30.0);
                holderButton.setPrefWidth(30.0);
                wallLine.add(holderButton, i, 0);
            } else {
                wallLine.add(button, i, 0);
            }
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
