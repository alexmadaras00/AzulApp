package messaging.executors.controller;

import messaging.dataobjects.GameState;
import messaging.dataobjects.RoundUpdate;
import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.messages.*;
import model.Model;
import utils.ExceptionGameStart;

public class StartGameExecutor implements Executor {
    private StartGame message;
    private Model model;

    @Override
    public Message execute(Executable executable) {
        if (message == null || !(executable instanceof Model)) {
            return null;
        }
        model = (Model) executable;
        try {
            GameState gameState = model.startGame();
            RoundUpdate roundStart = model.startRound();
            OkStartGame okStartGame = new OkStartGame(message.getId());
            okStartGame.setGameState(gameState);
            okStartGame.setRoundUpdate(roundStart);
            return okStartGame;
        } catch (ExceptionGameStart e) {
            BadRequest badRequest = new BadRequest(message.getId());
            badRequest.setReason(e.getMessage());
            return badRequest;
        }
    }

    @Override
    public void setMessage(Message message) {
        if (message instanceof StartGame) {
            this.message = (StartGame) message;
        }
    }

}
