package messaging.executors;

import messaging.executors.controller.JoinGameExecutor;
import messaging.executors.controller.PlayerMoveExecutor;
import messaging.executors.controller.StartGameExecutor;
import messaging.executors.view.BadRequestExecutor;
import messaging.executors.view.OkJoinGameExecutor;
import messaging.executors.view.OkPlayerMoveExecutor;
import messaging.executors.view.OkStartGameExecutor;
import messaging.messages.*;

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
        } else {
            executor = new BadRequestExecutor();
        }
        executor.setMessage(message);
        return executor;
    }

}
