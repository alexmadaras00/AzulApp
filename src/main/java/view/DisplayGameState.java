package view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.TileColor;

public class DisplayGameState extends HBox {

    public List<DisplayFactory> factories;
    public DisplayMiddle middle;
    public List<DisplayPlayer> players;
    public int activePlayer;
    public List<List<TileColor>> wallTemplate;

    private GridPane playerView;
    private VBox factoryView;

    public DisplayGameState() {
        factories = new ArrayList<DisplayFactory>();
        middle = new DisplayMiddle();
        activePlayer = 0;
        players = new ArrayList<DisplayPlayer>();
        setupPlayerView();
        setupFactoryView();
        this.setSpacing(40);
        this.setPadding(new Insets(30, 30, 30, 30));
        getChildren().addAll(factoryView, middle, playerView);
    }

    private void setupPlayerView() {
        playerView = new GridPane();
        playerView.setHgap(20);
        playerView.setVgap(40);
    }

    private void setupFactoryView() {
        factoryView = new VBox();
        factoryView.setSpacing(20);
    }

    public DisplayPlayer getPlayer(int playerID) {
        for (DisplayPlayer player : players) {
            if (player.id == playerID) {
                return player;
            }
        }
        return null;
    }

    public DisplayFactory getFactory(int factoryID) {
        for (DisplayFactory factory : factories) {
            if (factory.id == factoryID) {
                return factory;
            }
        }
        return null;
    }

    public void setWallPattern(List<List<TileColor>> wallTemplate) {
        this.wallTemplate = wallTemplate;
    }

    public void addPlayer(int id, String name) {
        DisplayPlayer player = new DisplayPlayer(id, name, wallTemplate);
        players.add(player);
        playerView.add(player, players.size() % 2, (players.size() - 1) / 2);
    }

    public void addFactory(int id) {
        DisplayFactory factory = new DisplayFactory(id);
        factories.add(factory);
        factoryView.getChildren().add(factory);

    }

    public void setActivePlayer(int playerID) {
        activePlayer = players.indexOf(getPlayer(playerID));
    }
}
