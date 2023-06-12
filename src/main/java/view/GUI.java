package view;

import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.AzulApp;
import model.Game;
import model.Tile;
import model.TileColor;

public class GUI extends Stage implements UI {
    private DisplayGameState gameState;
    private Scene scene;
    private GamePage gamePageController;
    private Parent hubPage;
    private Parent gamePage;
    private HubPage hubPageController;
    private Stage stage;

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        gameState = new DisplayGameState();
        FXMLLoader loaderHub = new FXMLLoader(AzulApp.class.getResource("/view/HubPage.fxml"));
        FXMLLoader loaderGame = new FXMLLoader(AzulApp.class.getResource("/view/GamePage.fxml"));

        hubPage = loaderHub.load();
        gamePage = loaderGame.load();
        hubPageController = loaderHub.getController();
        gamePageController = loaderGame.getController();
        scene = new Scene(hubPage);
        this.stage.setScene(scene);
        this.stage.show();
    }

    @Override
    public void startGame() {
        scene = new Scene(gamePage);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void connectMessager(Messager messager) {
        return;
    }

    @Override
    public void setWallPattern(List<List<TileColor>> pattern) {
        gameState.setWallPattern(pattern);
    }

    @Override
    public void addTileFactory(int factory, Tile tile) {
        gameState.factories.get(factory).addTile(tile);
    }

    @Override
    public void removeTilesFactory(int factory, Tile tile) {
        gameState.factories.get(factory).removeTiles(tile);
    }

    @Override
    public void clearFactory(int factory) {
        gameState.factories.get(factory).clear();
    }

    @Override
    public void addTileMiddle(Tile tile) {
        gameState.middle.addTile(tile);
    }

    @Override
    public void removeTilesMiddle(Tile tile) {
        gameState.middle.removeTiles(tile);
    }

    @Override
    public void clearMiddle() {
        for (TileColor TileColor : TileColor.values()) {
            gameState.middle.removeTiles(TileColor);
        }
    }

    @Override
    public void clearAll() {
        this.clearMiddle();
        gameState.factories.forEach((DisplayFactory factory) -> {
            factory.clear();
        });
        gameState.players.forEach((DisplayPlayer player) -> {
            clearPlayer(player.id);
        });
    }

    @Override
    public void commit() {
        return;
    }

    @Override
    public void addTileWall(int playerID, int row, Tile tile) {
        gameState.getPlayer(playerID).wall.addTile(row, tile);
    }

    @Override
    public void removeTilesWall(int playerID, int row, Tile tile) {
        gameState.getPlayer(playerID).wall.removeTile(row, tile);
    }

    @Override
    public void clearWall(int playerID) {
        gameState.getPlayer(playerID).wall.clear();
    }

    @Override
    public void addTilePattern(int playerID, int row, Tile tile) {
        gameState.getPlayer(playerID).patternLine.addTile(row, tile);
    }

    @Override
    public void removeTilePattern(int playerID, int row, Tile tile) {
        gameState.getPlayer(playerID).patternLine.removeTile(row);

    }

    @Override
    public void clearPatternLine(int playerID, int row) {
        gameState.getPlayer(playerID).patternLine.clearRow(row);
    }

    @Override
    public void clearPattern(int playerID) {
        gameState.getPlayer(playerID).patternLine.clear();
    }

    @Override
    public void addTileFloorLine(int playerID, Tile tile) {
        gameState.getPlayer(playerID).floorLine.addTile(tile);
    }

    @Override
    public void removeTilesFloorLine(int playerID, Tile tile) {
        gameState.getPlayer(playerID).floorLine.removeTiles(tile);

    }

    @Override
    public void clearFloorLine(int playerID) {
        gameState.getPlayer(playerID).floorLine.clear();
    }

    @Override
    public void setPlayerName(int playerID, String name) {
        gameState.getPlayer(playerID).name = name;
    }

    @Override
    public void setScore(int playerID, int score) {
        gameState.getPlayer(playerID).score = score;

    }

    @Override
    public void clearPlayer(int playerID) {
        DisplayPlayer player = gameState.getPlayer(playerID);
        player.floorLine.clear();
        player.wall.clear();
        player.patternLine.clear();
        player.score = 0;
    }

    @Override
    public void resetGameState() {
        gameState = new DisplayGameState();
    }

    @Override
    public void setActivePlayerView(int playerID) {
        gameState.setActivePlayer(playerID);
    }

    @Override
    public void addFactory(int factoryID) {
        gameState.addFactory(factoryID);
    }

    @Override
    public void addPlayer(int playerID, String name) {
        gamePage.addPlayer(playerID, name);
    }

}
