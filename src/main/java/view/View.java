package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.AzulApp;

public class View {

    private Stage stage = null;
    private GamePage gamePageController = null;
    private HubPage hubPageController = null;
    private Parent hubPageView = null;
    private Parent gamePageView = null;
    private Object currentPage = null;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public GamePage getGamePageController() {
        return gamePageController;
    }

    public void setGamePageController(GamePage gamePage) {
        this.gamePageController = gamePage;
    }

    public HubPage getHubPageController() {
        return hubPageController;
    }

    public void setHubPageController(HubPage hubPage) {
        this.hubPageController = hubPage;
    }

    public Parent getGamePageView() {
        return gamePageView;
    }

    public void setGamePageView(Parent gamePageView) {
        this.gamePageView = gamePageView;
    }

    public Parent getHubPageView() {
        return hubPageView;
    }

    public void setHubPageView(Parent hubPageView) {
        this.hubPageView = hubPageView;
    }

    public void showHub() {
        Scene scene = new Scene(this.getHubPageView());
        this.getStage().setScene(scene);
        this.getStage().show();
        this.currentPage = this.hubPageController;
    }

    public void showGame() {
        Scene scene = new Scene(this.getGamePageView());
        this.getStage().setScene(scene);
        this.getStage().show();
        this.currentPage = this.gamePageController;
    }

    public void toast(String msg) {
        if (this.currentPage instanceof GamePage) {
            gamePageController.toast(msg);
        } else if (this.currentPage instanceof HubPage) {
            hubPageController.toast(msg);
        } else {
            return;
        }
    }

    public void update() {
        if (this.currentPage instanceof GamePage) {
            this.gamePageController.update();
        } else if (this.currentPage instanceof HubPage) {
            this.hubPageController.update();
        } else {
            return;
        }
    }

    public void setup(Stage stage) throws Exception {
        setStage(stage);
        FXMLLoader loaderHub = new FXMLLoader(AzulApp.class.getResource("/view/HubPage.fxml"));
        FXMLLoader loaderGame = new FXMLLoader(AzulApp.class.getResource("/view/GamePage.fxml"));
        setHubPageController(loaderHub.getController());
        setGamePageController(loaderGame.getController());
        setHubPageView(loaderHub.load());
        setGamePageView(loaderGame.load());
    }
}
