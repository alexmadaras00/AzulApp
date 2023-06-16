package view;

import java.net.URL;

import controller.Controller;
import controller.ModelProxy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI implements View {
    private ModelProxy model;

    private Controller controller;

    private ViewUpdateListener listener;

    private Stage stage;
    private GamePage gamePageController;
    private HubPage hubPageController;
    private Parent hubPageView;
    private Parent gamePageView;
    private Object currentPage;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
        listener = new ViewUpdateListener(this);
        controller.addListener(listener);
        model = controller.getProxy();
    }

    public void showHub() {
        Scene scene = new Scene(hubPageView);
        stage.setScene(scene);
        stage.show();
        hubPageController.update();
        this.currentPage = this.hubPageController;
    }

    public void showGame() {
        Scene scene = new Scene(gamePageView);
        stage.setScene(scene);
        stage.show();
        gamePageController.update();
        this.currentPage = this.gamePageController;
    }

    @Override
    public void toast(String message) {
        if (this.currentPage instanceof GamePage) {
            gamePageController.toast(message);
        } else if (this.currentPage instanceof HubPage) {
            hubPageController.toast(message);
        } else {
            return;
        }
    }

    @Override
    public void update() {
        if (this.currentPage instanceof GamePage) {
            this.gamePageController.update();
        } else if (this.currentPage instanceof HubPage) {
            this.hubPageController.update();
        } else {
            return;
        }
    }

    public void setup(Stage stage, URL fxmlUrlHub, URL fxmlUrlGame) throws Exception {
        this.stage = stage;
        FXMLLoader loaderHub = new FXMLLoader(fxmlUrlHub);
        FXMLLoader loaderGame = new FXMLLoader(fxmlUrlGame);
        hubPageView = loaderHub.load();
        gamePageView = loaderGame.load();
        hubPageController = loaderHub.getController();
        gamePageController = loaderGame.getController();
        hubPageController.setView(this);
        hubPageController.setController(controller);
        hubPageController.setModel(model);
        gamePageController.setController(controller);
        gamePageController.setModel(model);
    }
}
