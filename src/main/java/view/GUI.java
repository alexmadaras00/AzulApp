package view;

import java.util.List;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Tile;
import model.TileColor;

public class GUI extends Stage implements UI {
    private DisplayGameState gameState;
    private Messager messager;


    public void start(Stage stage) {
        gameState = new DisplayGameState();
        Scene scene = new Scene(gameState);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void connectMessager(Messager messager) {
        this.messager = messager;
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
        gameState.addPlayer(playerID, name);

    }


}
