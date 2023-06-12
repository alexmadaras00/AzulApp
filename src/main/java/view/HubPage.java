package view;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import messaging.messages.JoinGame;
import messaging.messages.StartGame;

public class HubPage {
    private List<Integer> disables;
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

    @FXML
    void joinPlayer1(ActionEvent event) {
        disablePlayer(1);
        JoinGame message = new JoinGame(playerName1.getText());
        View.getInstance().send(message);
    }

    @FXML
    void joinPlayer2(ActionEvent event) {
        disablePlayer(2);
        JoinGame message = new JoinGame(playerName2.getText());
        View.getInstance().send(message);
    }

    @FXML
    void joinPlayer3(ActionEvent event) {
        disablePlayer(3);
        JoinGame message = new JoinGame(playerName3.getText());
        View.getInstance().send(message);
    }

    @FXML
    void joinPlayer4(ActionEvent event) {
        disablePlayer(4);
        JoinGame message = new JoinGame(playerName4.getText());
        View.getInstance().send(message);
    }

    @FXML
    void startGame(ActionEvent event) {
        startButton.setDisable(true);
        StartGame message = new StartGame();
        View.getInstance().send(message);
    }

    void disablePlayer(int place) {
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
        disables.add(place);
    }

    public void undoLastDisable() {
        try {
            int lastDisable = disables.get(disables.size() - 1);
            switch (lastDisable) {
                case 1:
                    joinButton1.setDisable(false);
                    playerName1.setEditable(true);
                    break;
                case 2:
                    joinButton2.setDisable(false);
                    playerName2.setEditable(true);
                    break;
                case 3:
                    joinButton3.setDisable(false);
                    playerName3.setEditable(true);
                    break;
                case 4:
                    joinButton4.setDisable(false);
                    playerName4.setEditable(true);
                    break;
                default:
                    return;
            }
            disables.remove(disables.size() - 1);
        } catch (Exception e) {
            return;
        }

    }

}
