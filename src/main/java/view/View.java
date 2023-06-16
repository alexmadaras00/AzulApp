package view;

import java.net.URL;

import controller.ControllerImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ModelProxy;

public class View {
    private ModelProxy model;

    private ControllerImpl controllerImpl;

    private Stage stage;
    private GamePage gamePageController;
    private HubPage hubPageController;
    private Parent hubPageView;
    private Parent gamePageView;
    private Object currentPage;

    public void setModel(ModelProxy model) {
        this.model = model;
    }

    public void setController(ControllerImpl controllerImpl) {
        this.controllerImpl = controllerImpl;
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

    public void setup(Stage stage, URL fxmlUrlHub, URL fxmlUrlGame) throws Exception {
        this.stage = stage;
        FXMLLoader loaderHub = new FXMLLoader(fxmlUrlHub);
        FXMLLoader loaderGame = new FXMLLoader(fxmlUrlGame);
        hubPageView = loaderHub.load();
        gamePageView = loaderGame.load();
        hubPageController = loaderHub.getController();
        gamePageController = loaderGame.getController();
        hubPageController.setView(this);
        hubPageController.setController(controllerImpl);
        hubPageController.setModel(model);
        gamePageController.setView(this);
        gamePageController.setController(controllerImpl);
        gamePageController.setModel(model);
    }
}
