package view;

import java.util.List;

import controller.ControllerImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.ModelProxy;
import model.Player;

public class HubPage {
    private ModelProxy model;
    private ControllerImpl controllerImpl;
    private View view;

    @FXML
    private Button joinButton1;

    @FXML
    private Button joinButton2;

    @FXML
    private Button joinButton3;

    @FXML
    private Button joinButton4;

    @FXML
    private TextField playerName1;

    @FXML
    private TextField playerName2;

    @FXML
    private TextField playerName3;

    @FXML
    private TextField playerName4;

    @FXML
    private Button startButton;

    void update() {
        List<Player> players = model.getPlayerList();
        enableAllPlayers();
        for (Player player : players) {
            if (playerName1.getText().equals(player.getName())) {
                disablePlayer(1);
            } else if (playerName2.getText().equals(player.getName())) {
                disablePlayer(2);
            } else if (playerName3.getText().equals(player.getName())) {
                disablePlayer(3);
            } else if (playerName4.getText().equals(player.getName())) {
                disablePlayer(4);
            }
        }

    }

    void toast(String text) {
        System.out.println(text);
    }

    private void disablePlayer(int place) {
        switch (place) {
            case 1:
                joinButton1.setDisable(true);
                playerName1.setEditable(false);
                break;
            case 2:
                joinButton2.setDisable(true);
                playerName2.setEditable(false);
                break;
            case 3:
                joinButton3.setDisable(true);
                playerName3.setEditable(false);
                break;
            case 4:
                joinButton4.setDisable(true);
                playerName4.setEditable(false);
                break;
            default:
                return;
        }
    }

    private void enableAllPlayers() {
        joinButton1.setDisable(false);
        playerName1.setEditable(true);
        joinButton2.setDisable(false);
        playerName2.setEditable(true);
        joinButton3.setDisable(false);
        playerName3.setEditable(true);
        joinButton4.setDisable(false);
        playerName4.setEditable(true);
    }

    @FXML
    void joinPlayer1(ActionEvent event) {
        if (playerName1.getText() == null || playerName1.getText() == "") {
            toast("no name provided");
            return;
        }
        controllerImpl.joinPlayer(playerName1.getText());

    }

    @FXML
    void joinPlayer2(ActionEvent event) {
        if (playerName2.getText() == null || playerName2.getText() == "") {
            toast("no name provided");
            return;
        }
        controllerImpl.joinPlayer(playerName2.getText());
    }

    @FXML
    void joinPlayer3(ActionEvent event) {
        if (playerName3.getText() == null || playerName3.getText() == "") {
            toast("no name provided");
            return;
        }
        controllerImpl.joinPlayer(playerName3.getText());
    }

    @FXML
    void joinPlayer4(ActionEvent event) {
        if (playerName4.getText() == null || playerName4.getText() == "") {
            toast("no name provided");
            return;
        }
        controllerImpl.joinPlayer(playerName4.getText());
    }

    @FXML
    void startGame(ActionEvent event) {
        controllerImpl.startGame();
        if (model.isPlaying()) {
            view.showGame();
        }
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setModel(ModelProxy model) {
        this.model = model;
    }

    public void setController(ControllerImpl controllerImpl) {
        this.controllerImpl = controllerImpl;
    }
}
