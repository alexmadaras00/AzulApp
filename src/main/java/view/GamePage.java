package view;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GamePage {

    public List<PlayerGUI> players;
    public List<Integer> factoryIDs;

    GridPane getFactory(int factoryID) {
        for (int i = 0; i < factoryIDs.size(); i++) {
            if (factoryIDs.get(i) == factoryID) {
                switch (i) {
                    case 0:
                        return factory1;
                    case 1:
                        return factory2;
                    case 2:
                        return factory3;
                    case 3:
                        return factory4;
                    case 4:
                        return factory5;
                    case 5:
                        return factory6;
                    case 6:
                        return factory7;
                    case 7:
                        return factory8;
                    case 8:
                        return factory9;
                    default:
                        return null;

                }
            }
        }
        return null;
    }

    VBox getMiddle() {
        return middle;
    }

    PlayerGUI getPlayer(int playerID) {
        for (PlayerGUI player : players) {
            if (player.getId() == playerID) {
                return player;
            }
        }
        return null;
    }

    void addPlayer(int playerID, String name) {
        players.add(new PlayerGUI(players.size() + 1, playerID));
        this.getPlayer(playerID).getName().setText(name);
    }

    @FXML
    private Button buttonF1T1;
    private Button buttonF1T2;
    private Button buttonF1T3;
    private Button buttonF1T4;
    private Button buttonF2T1;
    private Button buttonF2T2;
    private Button buttonF2T3;
    private Button buttonF2T4;
    private Button buttonF3T1;
    private Button buttonF3T2;
    private Button buttonF3T3;
    private Button buttonF3T4;
    private Button buttonF4T1;
    private Button buttonF4T2;
    private Button buttonF4T3;
    private Button buttonF4T4;
    private Button buttonF5T1;
    private Button buttonF5T2;
    private Button buttonF5T3;
    private Button buttonF5T4;
    private Button buttonF6T1;
    private Button buttonF6T2;
    private Button buttonF6T3;
    private Button buttonF6T4;
    private Button buttonF7T1;
    private Button buttonF7T2;
    private Button buttonF7T3;
    private Button buttonF7T4;
    private Button buttonF8T1;
    private Button buttonF8T2;
    private Button buttonF8T3;
    private Button buttonF8T4;
    private Button buttonF9T1;
    private Button buttonF9T2;
    private Button buttonF9T3;
    private Button buttonF9T4;
    private GridPane factory1;
    private GridPane factory2;
    private GridPane factory3;
    private GridPane factory4;
    private GridPane factory5;
    private GridPane factory6;
    private GridPane factory7;
    private GridPane factory8;
    private GridPane factory9;
    private VBox middle;
    private HBox player1Floor;
    private Label player1Name;
    private GridPane player1PL1;
    private GridPane player1PL2;
    private GridPane player1PL3;
    private GridPane player1PL4;
    private GridPane player1PL5;
    private Label player1Score;
    private GridPane player1W1;
    private GridPane player1W2;
    private GridPane player1W3;
    private GridPane player1W4;
    private GridPane player1W5;
    private HBox player2Floor;
    private Label player2Name;
    private GridPane player2PL1;
    private GridPane player2PL2;
    private GridPane player2PL3;
    private GridPane player2PL4;
    private GridPane player2PL5;
    private Label player2Score;
    private GridPane player2W1;
    private GridPane player2W2;
    private GridPane player2W3;
    private GridPane player2W4;
    private GridPane player2W5;
    private HBox player3Floor;
    private Label player3Name;
    private GridPane player3PL1;
    private GridPane player3PL2;
    private GridPane player3PL3;
    private GridPane player3PL4;
    private GridPane player3PL5;
    private Label player3Score;
    private GridPane player3W1;
    private GridPane player3W2;
    private GridPane player3W3;
    private GridPane player3W4;
    private GridPane player3W5;
    private HBox player4Floor;
    private Label player4Name;
    private GridPane player4PL1;
    private GridPane player4PL2;
    private GridPane player4PL3;
    private GridPane player4PL4;
    private GridPane player4PL5;
    private Label player4Score;
    private GridPane player4W1;
    private GridPane player4W2;
    private GridPane player4W3;
    private GridPane player4W4;
    private GridPane player4W5;
    private VBox playerboard1;
    private VBox playerboard2;
    private VBox playerboard3;
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

    void Gamepage() {

    }

    @FXML
    void selectTile(ActionEvent event) {
        // TODO - check which location it comes from by doing .getParent() is
        // TODO factory1,2,3,4,5 or middle
        // TODO - select or deselect it,
        // TODO - create partial data object to send (from location, tile, amount, etc)
    }

    @FXML
    void selectToLocation(ActionEvent event) {
        // TODO - check which location it comes from by doing .getParent()
        // TODO - can we select destination (is some tile selected?)
        // TODO - fill in data object to send
        // TODO - send Message
    }
}
