package view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import controller.ControllerImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.ModelProxy;
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

    public List<PlayerGUI> players;
    public List<FactoryGUI> factories;

    class TileButton extends Button {
        public TileButton(Tile tile) {
            this.setBackground(
                    Background.fill(tile instanceof TileColor ? translateColor((TileColor) tile) : Color.ANTIQUEWHITE));
        }
    }

    class FactoryGUI {
        private int factoryID;
        private int place;

        public FactoryGUI(int place, int factoryID) {
            this.place = place;
            this.factoryID = factoryID;
        }

        public int getId() {
            return factoryID;
        }

        public void setTiles(List<TileColor> tiles) {
            if (tiles.size() != 4) {
                return;
            }
            if (place > 0 && place < 10) {
                getElementByName("buttonF" + place + "T1", Button.class).setBackground(Background.fill(translateColor(tiles.get(0))));
                getElementByName("buttonF" + place + "T2", Button.class).setBackground(Background.fill(translateColor(tiles.get(1))));
                getElementByName("buttonF" + place + "T3", Button.class).setBackground(Background.fill(translateColor(tiles.get(2))));
                getElementByName("buttonF" + place + "T4", Button.class).setBackground(Background.fill(translateColor(tiles.get(3))));
            }
        }

        public void removeTiles() {
            return;
        }

        public void clear() {
            if (place > 0 && place < 10) {
                getElementByName("buttonF" + place + "T1", Button.class).setBackground(Background.EMPTY);
                getElementByName("buttonF" + place + "T2", Button.class).setBackground(Background.EMPTY);
                getElementByName("buttonF" + place + "T3", Button.class).setBackground(Background.EMPTY);
                getElementByName("buttonF" + place + "T4", Button.class).setBackground(Background.EMPTY);
            }  
        }
    }

    public GamePage() {
        players = new ArrayList<>();
        factories = new ArrayList<>();
    }

    VBox getMiddle() {
        return middle;
    }

    void addPlayer(int playerID, String name) {
        players.add(new PlayerGUI(players.size() + 1, playerID));
        this.getPlayer(playerID).getName().setText(name);
    }

    PlayerGUI getPlayer(int playerID) {
        for (PlayerGUI player : players) {
            if (player.getId() == playerID) {
                return player;
            }
        }
        return null;
    }

    void addFactory(int factoryID) {
        factories.add(new FactoryGUI(factories.size() + 1, factoryID));
    }

    FactoryGUI getFactory(int factoryID) {
        for (FactoryGUI factory : factories) {
            if (factory.getId() == factoryID) {
                return factory;
            }
        }
        return null;
    }

    void addToMiddle(Tile tile) {
        middle.getChildren().add(new TileButton(tile));
    }

    void removeTilesMiddle(Tile tile) {
        middle.getChildren().removeIf((Node n) -> {
            Paint color = ((Button) n).getBackground().getFills().get(0).getFill();
            TileColor tileColor = translateColorBack((Color) color);
            if (tileColor == null && translateColor(tile) == null) {
                return true;
            } else if (tileColor != null && tile == tileColor) {
                return true;
            }
            return false;
        });
    }

    public static Color translateColor(Tile tile) {
        if (!(tile instanceof TileColor)) {
            return null;
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
        }
        // should never run
        return null;
    }

    public static TileColor translateColorBack(Color color) {
        if (color == Color.RED) {
            return TileColor.RED;
        } else if (color == Color.BLACK) {
            return TileColor.BLACK;

        } else if (color == Color.BLUE) {
            return TileColor.BLUE;

        } else if (color == Color.YELLOW) {
            return TileColor.YELLOW;

        } else if (color == Color.CYAN) {
            return TileColor.CYAN;
        }
        return null;

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
            if (place < 1 || place > 4 || row < 0 || row  > 4) { 
                return null;
            }
            return getElementByName("player" + place + "W" + (row+1), GridPane.class);
        }

        GridPane getPatternline(int row) {
            if (place < 1 || place > 4 || row < 0 || row  > 4) { 
                return null;
            }
            return getElementByName("player" + place + "PL" + (row+1), GridPane.class);
        }

        HBox getFloorline() {
            if (place < 0 || place > 4) {
                return null;
            }
            return getElementByName("player" + place + "Floor", HBox.class);
        }

        Label getScore() {
            if (place < 0 || place > 4) {
                return null;
            }
            return getElementByName("player" + place + "Score", Label.class);
        }

        Label getName() {
            if (place < 0 || place > 4) {
                return null;
            }
            return getElementByName("player" + place + "Name", Label.class);
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

    public void disableRestFactories() {
        switch (factories.size()) {
            case 5:
                factory7.setVisible(false);
                factory6.setVisible(false);
            case 7:
                factory9.setVisible(false);
                factory8.setVisible(false);
            default:
                return;
        }
    }

    public void disableRestPlayers() {
        List<Integer> places = new ArrayList<Integer>();
        for (PlayerGUI player : players) {
            places.add(player.place);
        }
        for (int i = 1; i < 5; i++) {
            if (!places.contains(i)) {
                getElementByName("playerboard" + i, VBox.class).setVisible(false);
            }
        }
    }

    public void toast(String msg) {
        System.out.println(msg);
    }

    public void update() {
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
