package messaging.executors.controller;

import messaging.dataobjects.PlayerData;
import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.messages.BadRequest;
import messaging.messages.JoinGame;
import messaging.messages.Message;
import messaging.messages.OkJoinGame;
import model.Model;
import model.Player;

public class JoinGameExecutor implements Executor {
    private JoinGame message;
    private Model model;

    public static final String reasonNoName = "No Player name provided.";
    public static final String reasonMaxPlayers = "Game is full.";
    public static final String reasonGameAlreadyStated = "Game has already started.";

    @Override
    public Message execute(Executable executable) {
        if (message == null || !(executable instanceof Model)) {
            return null;
        }
        model = (Model) executable;

        if (model.isPlaying()) {
            BadRequest response = new BadRequest(message.getId());
            response.setReason(reasonGameAlreadyStated);
            return response;
        }
        if (model.getPlayers().size() >= 4) {
            BadRequest response = new BadRequest(message.getId());
            response.setReason(reasonMaxPlayers);
            return response;
        }
        if (message.getPlayerName() == null || message.getPlayerName() == "") {
            BadRequest response = new BadRequest(message.getId());
            response.setReason(reasonNoName);
            return response;
        }
        PlayerData playerData = model.addPlayer(new Player(message.getPlayerName()));
        OkJoinGame response = new OkJoinGame(message.getId());
        response.setPlayerData(playerData);
        return response;
    }

    @Override
    public void setMessage(Message message) {
        if (message instanceof JoinGame) {
            this.message = (JoinGame) message;
        }
    }

}