package view;

import java.util.List;

import controller.Controller;
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
import model.Model;
import model.Player;
import model.PlayerTile;
import model.Tile;
import model.TileColor;
import model.factory.Factory;

public class GamePage {
    private Model model;

    private Controller controller;

    private View view;

    public void setView(View view) {
        this.view = view;
    }

    public void setModel(Model model) {
        this.model = model;
    }

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

    class PlayerGUI {
        private int place;
        private int id;

        public PlayerGUI(int place, int id) {
            this.place = place;
            this.id = id;
        }

        int getId() {
            return this.id;
        }

        GridPane getWall(int row) {
            switch (place) {
                case 1:
                    switch (row) {
                        case 0:
                            return player1W1;
                        case 1:
                            return player1W2;
                        case 2:
                            return player1W3;
                        case 3:
                            return player1W4;
                        case 4:
                            return player1W5;
                        default:
                            return null;
                    }
                case 2:
                    switch (row) {
                        case 0:
                            return player2W1;
                        case 1:
                            return player2W2;
                        case 2:
                            return player2W3;
                        case 3:
                            return player2W4;
                        case 4:
                            return player2W5;
                        default:
                            return null;
                    }
                case 3:
                    switch (row) {
                        case 0:
                            return player3W1;
                        case 1:
                            return player3W2;
                        case 2:
                            return player3W3;
                        case 3:
                            return player3W4;
                        case 4:
                            return player3W5;
                        default:
                            return null;
                    }
                case 4:
                    switch (row) {
                        case 0:
                            return player4W1;
                        case 1:
                            return player4W2;
                        case 2:
                            return player4W3;
                        case 3:
                            return player4W4;
                        case 4:
                            return player4W5;
                        default:
                            return null;
                    }
                default:
                    return null;
            }
        }

        GridPane getPatternline(int row) {
            switch (place) {
                case 1:
                    switch (row) {
                        case 0:
                            return player1PL1;
                        case 1:
                            return player1PL2;
                        case 2:
                            return player1PL3;
                        case 3:
                            return player1PL4;
                        case 4:
                            return player1PL5;
                        default:
                            return null;
                    }
                case 2:
                    switch (row) {
                        case 0:
                            return player2PL1;
                        case 1:
                            return player2PL2;
                        case 2:
                            return player2PL3;
                        case 3:
                            return player2PL4;
                        case 4:
                            return player2PL5;
                        default:
                            return null;
                    }
                case 3:
                    switch (row) {
                        case 0:
                            return player3PL1;
                        case 1:
                            return player3PL2;
                        case 2:
                            return player3PL3;
                        case 3:
                            return player3PL4;
                        case 4:
                            return player3PL5;
                        default:
                            return null;
                    }
                case 4:
                    switch (row) {
                        case 0:
                            return player4PL1;
                        case 1:
                            return player4PL2;
                        case 2:
                            return player4PL3;
                        case 3:
                            return player4PL4;
                        case 4:
                            return player4PL5;
                        default:
                            return null;
                    }
                default:
                    return null;
            }
        }

        HBox getFloorline() {
            switch (place) {
                case 1:
                    return player1Floor;
                case 2:
                    return player2Floor;

                case 3:
                    return player3Floor;

                case 4:
                    return player4Floor;

                default:
                    return null;
            }
        }

        Label getScore() {
            switch (place) {
                case 1:
                    return player1Score;
                case 2:
                    return player2Score;

                case 3:
                    return player3Score;

                case 4:
                    return player4Score;

                default:
                    return null;
            }
        }

        Label getName() {
            switch (place) {
                case 1:
                    return player1Name;
                case 2:
                    return player2Name;

                case 3:
                    return player3Name;

                case 4:
                    return player4Name;

                default:
                    return null;
            }
        }

    }

    @FXML
    void selectTile(ActionEvent event) {

    }

    @FXML
    void selectToLocation(ActionEvent event) {

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
        List<Factory> factories = model.getFactories();
        if (factories.size() <= 7) {
            if (factories.size() <= 5) {
                factory7.setVisible(false);
                factory6.setVisible(false);
            }
            factory9.setVisible(false);
            factory8.setVisible(false);
        }
        List<TileColor> factoryTiles = null;

        switch (factories.size()) {
            case 9:
                factoryTiles = factories.get(8).getAllTiles();
                setTileColor(buttonF9T1, factoryTiles.get(0));
                setTileColor(buttonF9T2, factoryTiles.get(1));
                setTileColor(buttonF9T3, factoryTiles.get(2));
                setTileColor(buttonF9T4, factoryTiles.get(3));

                factoryTiles = factories.get(7).getAllTiles();
                setTileColor(buttonF8T1, factoryTiles.get(0));
                setTileColor(buttonF8T2, factoryTiles.get(1));
                setTileColor(buttonF8T3, factoryTiles.get(2));
                setTileColor(buttonF8T4, factoryTiles.get(3));

            case 7:
                factoryTiles = factories.get(6).getAllTiles();
                setTileColor(buttonF7T1, factoryTiles.get(0));
                setTileColor(buttonF7T2, factoryTiles.get(1));
                setTileColor(buttonF7T3, factoryTiles.get(2));
                setTileColor(buttonF7T4, factoryTiles.get(3));

                factoryTiles = factories.get(5).getAllTiles();
                setTileColor(buttonF6T1, factoryTiles.get(0));
                setTileColor(buttonF6T2, factoryTiles.get(1));
                setTileColor(buttonF6T3, factoryTiles.get(2));
                setTileColor(buttonF6T4, factoryTiles.get(3));

            case 5:
                factoryTiles = factories.get(4).getAllTiles();
                setTileColor(buttonF5T1, factoryTiles.get(0));
                setTileColor(buttonF5T2, factoryTiles.get(1));
                setTileColor(buttonF5T3, factoryTiles.get(2));
                setTileColor(buttonF5T4, factoryTiles.get(3));

                factoryTiles = factories.get(3).getAllTiles();
                setTileColor(buttonF4T1, factoryTiles.get(0));
                setTileColor(buttonF4T2, factoryTiles.get(1));
                setTileColor(buttonF4T3, factoryTiles.get(2));
                setTileColor(buttonF4T4, factoryTiles.get(3));

                factoryTiles = factories.get(2).getAllTiles();
                setTileColor(buttonF3T1, factoryTiles.get(0));
                setTileColor(buttonF3T2, factoryTiles.get(1));
                setTileColor(buttonF3T3, factoryTiles.get(2));
                setTileColor(buttonF3T4, factoryTiles.get(3));

                factoryTiles = factories.get(1).getAllTiles();
                setTileColor(buttonF2T1, factoryTiles.get(0));
                setTileColor(buttonF2T2, factoryTiles.get(1));
                setTileColor(buttonF2T3, factoryTiles.get(2));
                setTileColor(buttonF2T4, factoryTiles.get(3));
                factoryTiles = factories.get(0).getAllTiles();

                setTileColor(buttonF1T1, factoryTiles.get(0));
                setTileColor(buttonF1T2, factoryTiles.get(1));
                setTileColor(buttonF1T3, factoryTiles.get(2));
                setTileColor(buttonF1T4, factoryTiles.get(3));

            default:
                return;
        }
    }

    private class TileButton extends Button {

        TileButton(Tile tile) {
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
        List<Tile> tiles = model.getMiddle().getAllTiles();
        for (Tile tile : tiles) {
            middle.getChildren().add(new TileButton(tile));
        }
    }

    private void updatePlayers() {
        List<Player> players = model.getPlayers();
        switch (players.size()) {
            case 2:
                playerboard3.setVisible(false);
            case 3:
                playerboard4.setVisible(false);
            default:
                break;
        }
        switch (players.size()) {
            case 4:
                updatePlayer(players.get(3), 4);
            case 3:
                updatePlayer(players.get(2), 3);
            case 2:
                updatePlayer(players.get(1), 2);
                updatePlayer(players.get(0), 1);
            default:
                break;
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
        switch (place) {
            case 4:
                player4Score.setText("Score: " + player.getBoard().getScore());
                break;
            case 3:
                player3Score.setText("Score: " + player.getBoard().getScore());
                break;
            case 2:
                player2Score.setText("Score: " + player.getBoard().getScore());
                break;
            case 1:
                player1Score.setText("Score: " + player.getBoard().getScore());
                break;
            default:
                return;
        }
    }

    private void updateName(Player player, int place) {
        switch (place) {
            case 4:
                player4Name.setText(player.getName());
                break;
            case 3:
                player3Name.setText(player.getName());
                break;
            case 2:
                player2Name.setText(player.getName());
                break;
            case 1:
                player1Name.setText(player.getName());
                break;
            default:
                return;
        }
    }

    private void setFloorLine(HBox floorLine, List<Tile> tiles) {
        floorLine.getChildren().clear();
        for (Tile tile : tiles) {
            floorLine.getChildren().add(new TileButton(tile));
        }
    }

    private void updateFloorLine(Player player, int place) {
        List<Tile> floorTiles = player.getBoard().getFloorLine().getCopyTiles();
        switch (place) {
            case 4:
                setFloorLine(player4Floor, floorTiles);
                break;
            case 3:
                setFloorLine(player3Floor, floorTiles);
                break;
            case 2:
                setFloorLine(player2Floor, floorTiles);
                break;
            case 1:
                setFloorLine(player1Floor, floorTiles);
                break;
            default:
                return;
        }
    }

    private void updatePatternLine(Player player, int place) {
        List<List<Tile>> floorTiles = player.getBoard().getPatternLine().getCopyTable();
        switch (place) {
            case 1:
                setPatternLine(player1PL1, floorTiles.get(0));
                setPatternLine(player1PL2, floorTiles.get(1));
                setPatternLine(player1PL3, floorTiles.get(2));
                setPatternLine(player1PL4, floorTiles.get(3));
                setPatternLine(player1PL5, floorTiles.get(4));
                break;
            case 2:
                setPatternLine(player2PL1, floorTiles.get(0));
                setPatternLine(player2PL2, floorTiles.get(1));
                setPatternLine(player2PL3, floorTiles.get(2));
                setPatternLine(player2PL4, floorTiles.get(3));
                setPatternLine(player2PL5, floorTiles.get(4));
                break;
            case 3:
                setPatternLine(player3PL1, floorTiles.get(0));
                setPatternLine(player3PL2, floorTiles.get(1));
                setPatternLine(player3PL3, floorTiles.get(2));
                setPatternLine(player3PL4, floorTiles.get(3));
                setPatternLine(player3PL5, floorTiles.get(4));
                break;
            case 4:
                setPatternLine(player4PL1, floorTiles.get(0));
                setPatternLine(player4PL2, floorTiles.get(1));
                setPatternLine(player4PL3, floorTiles.get(2));
                setPatternLine(player4PL4, floorTiles.get(3));
                setPatternLine(player4PL5, floorTiles.get(4));
                break;
            default:
                return;
        }
    }

    private void setPatternLine(GridPane patternLine, List<Tile> tiles) {
        patternLine.getChildren().clear();
        switch (tiles.size()) {
            case 5:
                patternLine.add(new TileButton(tiles.get(4)), 0, 0);
            case 4:
                patternLine.add(new TileButton(tiles.get(3)), 1, 0);
            case 3:
                patternLine.add(new TileButton(tiles.get(2)), 2, 0);
            case 2:
                patternLine.add(new TileButton(tiles.get(1)), 3, 0);
            case 1:
                patternLine.add(new TileButton(tiles.get(0)), 4, 0);
            default:
                return;
        }
    }

    private void updateWall(Player player, int place) {
        List<List<Tile>> wallTiles = player.getBoard().getWall().getCopyTable();
        switch (place) {
            case 4:
                setWallLine(player4W1, wallTiles.get(0));
                setWallLine(player4W2, wallTiles.get(1));
                setWallLine(player4W3, wallTiles.get(2));
                setWallLine(player4W4, wallTiles.get(3));
                setWallLine(player4W5, wallTiles.get(4));
            case 3:
                setWallLine(player3W1, wallTiles.get(0));
                setWallLine(player3W2, wallTiles.get(1));
                setWallLine(player3W3, wallTiles.get(2));
                setWallLine(player3W4, wallTiles.get(3));
                setWallLine(player3W5, wallTiles.get(4));
            case 2:
                setWallLine(player2W1, wallTiles.get(0));
                setWallLine(player2W2, wallTiles.get(1));
                setWallLine(player2W3, wallTiles.get(2));
                setWallLine(player2W4, wallTiles.get(3));
                setWallLine(player2W5, wallTiles.get(4));
            case 1:
                setWallLine(player1W1, wallTiles.get(0));
                setWallLine(player1W2, wallTiles.get(1));
                setWallLine(player1W3, wallTiles.get(2));
                setWallLine(player1W4, wallTiles.get(3));
                setWallLine(player1W5, wallTiles.get(4));
            default:
                return;
        }
    }

    private void setWallLine(GridPane wallLine, List<Tile> tiles) {
        wallLine.getChildren().clear();
        wallLine.add(new TileButton(tiles.get(0)), 0, 0);
        wallLine.add(new TileButton(tiles.get(1)), 1, 0);
        wallLine.add(new TileButton(tiles.get(2)), 2, 0);
        wallLine.add(new TileButton(tiles.get(3)), 3, 0);
        wallLine.add(new TileButton(tiles.get(4)), 4, 0);
    }

    public void update() {
        updateFactories();
        updateMiddle();
        updatePlayers();
    }
}
