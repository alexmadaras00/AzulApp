package view;

import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.AzulApp;
import model.Tile;

public class GUI extends Stage implements UI {
    private Scene scene;
    private GamePage gamePageController;
    private Parent hubPage;
    private Parent gamePage;
    private HubPage hubPageController;
    private Stage stage;

    public void start(Stage stage) throws Exception {
        this.stage = stage;
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
    public void addPlayer(int playerID, String name) {
        // add to gamepage
        gamePageController.addPlayer(playerID, name);
        // confirm on hubpage
        showToast(name + " added");
    }

    @Override
    public void showToast(String message) {
        stage.setTitle(message);
    }

    @Override
    public void notAddPlayer(String playerName) {
        hubPageController.undoLastDisable();
    }

    @Override
    public void factorySetup(List<Integer> factoryIDs) {
        gamePageController.factoryIDs = factoryIDs;
        gamePageController.disableRestFactories();
    }

    @Override
    public void addTileFactory(int factoryID, Tile tile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTileFactory'");
    }

    @Override
    public void removeTilesFactory(int factoryID, Tile tile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeTilesFactory'");
    }

    @Override
    public void clearFactory(int factoryID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearFactory'");
    }

    @Override
    public void addTileMiddle(Tile tile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTileMiddle'");
    }

    @Override
    public void removeTilesMiddle(Tile tile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeTilesMiddle'");
    }

    @Override
    public void clearMiddle() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearMiddle'");
    }

    @Override
    public void setPlayerName(int playerID, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPlayerName'");
    }

    @Override
    public void setScore(int playerID, int score) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setScore'");
    }

    @Override
    public void setCurrentPlayer(int playerID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCurrentPlayer'");
    }

    @Override
    public void addTileWall(int PlayerID, int row, Tile tile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTileWall'");
    }

    @Override
    public void addTilePattern(int playerID, int row, Tile tile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTilePattern'");
    }

    @Override
    public void removeTilePattern(int playerID, int row, Tile tile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeTilePattern'");
    }

    @Override
    public void clearPatternLine(int playerID, int row) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearPatternLine'");
    }

    @Override
    public void addTileFloorLine(int playerID, Tile tile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTileFloorLine'");
    }

    @Override
    public void clearFloorLine(int playerID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearFloorLine'");
    }

}
