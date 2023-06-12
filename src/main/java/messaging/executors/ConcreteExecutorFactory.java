package messaging.executors;

import messaging.executors.controller.JoinGameExecutor;
import messaging.executors.controller.PlayerMoveExecutor;
import messaging.executors.controller.StartGameExecutor;
import messaging.executors.view.NotOkJoinGameExecutor;
import messaging.executors.view.NotOkPlayerMoveExecutor;
import messaging.executors.view.NotOkStartGameExecutor;
import messaging.executors.view.OkJoinGameExecutor;
import messaging.executors.view.OkPlayerMoveExecutor;
import messaging.executors.view.OkStartGameExecutor;
import messaging.messages.JoinGame;
import messaging.messages.Message;
import messaging.messages.NotOkJoinGame;
import messaging.messages.NotOkPlayerMove;
import messaging.messages.NotOkStartGame;
import messaging.messages.OkJoinGame;
import messaging.messages.OkPlayerMove;
import messaging.messages.OkStartGame;
import messaging.messages.PlayerMove;
import messaging.messages.StartGame;

public class ConcreteExecutorFactory implements ExecutorFactory {

    public Executor createExecutor(Message message) {
        Executor executor;
        if (message instanceof JoinGame) {
            executor = new JoinGameExecutor();
        } else if (message instanceof StartGame) {
            executor = new StartGameExecutor();
        } else if (message instanceof PlayerMove) {
            executor = new PlayerMoveExecutor();
        } else if (message instanceof OkJoinGame) {
            executor = new OkJoinGameExecutor();
        } else if (message instanceof OkStartGame) {
            executor = new OkStartGameExecutor();
        } else if (message instanceof OkPlayerMove) {
            executor = new OkPlayerMoveExecutor();
        } else if (message instanceof NotOkPlayerMove) {
            executor = new NotOkPlayerMoveExecutor();
        } else if (message instanceof NotOkStartGame) {
            executor = new NotOkStartGameExecutor();
        } else if (message instanceof NotOkJoinGame) {
            executor = new NotOkJoinGameExecutor();
        } else {
            return null;
        }
        executor.setMessage(message);
        return executor;
    }

}
