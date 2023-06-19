package view;

import java.net.URL;

import controller.Controller;
import controller.ControllerEventListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.ModelProxy;

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
    public Controller getController(){
        return this.controller;
    }

    public Stage getStage() {
        return stage;
    }
    public ControllerEventListener getListener() {
        return this.listener;
    }
    public ModelProxy getModel() {
        return model;
    }

    public Object getCurrentPage() {
        return currentPage;
    }

    public HubPage getHubPageController() {
        return hubPageController;
    }

    public Parent getHubPageView() {
        return hubPageView;
    }

    public GamePage getGamePageController() {
        return gamePageController;
    }

    public Parent getGamePageView() {
        return gamePageView;
    }

    public void setModel(ModelProxy model) {
        this.model = model;
    }

    public void showHub() {
        Scene scene = new Scene(this.hubPageView);
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
