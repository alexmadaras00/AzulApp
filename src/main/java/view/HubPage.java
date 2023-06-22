package view;

import java.util.List;
import java.util.Objects;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import shared.ModelProxy;
import shared.Player;

public class HubPage implements View{
    private ModelProxy model;
    private Controller controller;
    private GUI view;

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

    @Override
    public void update() {
        List<Player> players = model.getPlayers();
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

    @Override
    public void toast(String message) {
        System.out.println(message);
    }

    private void disablePlayer(int place) {
        switch (place) {
            case 1 -> {
                joinButton1.setDisable(true);
                playerName1.setEditable(false);
            }
            case 2 -> {
                joinButton2.setDisable(true);
                playerName2.setEditable(false);
            }
            case 3 -> {
                joinButton3.setDisable(true);
                playerName3.setEditable(false);
            }
            case 4 -> {
                joinButton4.setDisable(true);
                playerName4.setEditable(false);
            }
            default -> {
            }
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
    void joinPlayer1() {
        if (playerName1.getText() == null || Objects.equals(playerName1.getText(), "")) {
            toast("no name provided");
            return;
        }
        controller.joinPlayer(playerName1.getText());

    }

    @FXML
    void joinPlayer2() {
        if (playerName2.getText() == null || Objects.equals(playerName2.getText(), "")) {
            toast("no name provided");
            return;
        }
        controller.joinPlayer(playerName2.getText());
    }

    @FXML
    void joinPlayer3() {
        if (playerName3.getText() == null || Objects.equals(playerName3.getText(), "")) {
            toast("no name provided");
            return;
        }
        controller.joinPlayer(playerName3.getText());
    }

    @FXML
    void joinPlayer4() {
        if (playerName4.getText() == null || Objects.equals(playerName4.getText(), "")) {
            toast("no name provided");
            return;
        }
        controller.joinPlayer(playerName4.getText());
    }

    @FXML
    void startGame(ActionEvent event) {
        controller.startGame();
        if (model.isPlaying()) {
            view.showGame();
        }
    }

    public void setView(GUI view) {
        this.view = view;
    }

    public void setModel(ModelProxy model) {
        this.model = model;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
