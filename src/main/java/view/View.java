package view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
}
