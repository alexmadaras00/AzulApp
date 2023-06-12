package messaging.executors.controller;

import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.messages.Message;
import messaging.messages.PlayerMove;

public class PlayerMoveExecutor implements Executor {
    private PlayerMove message;

    @Override
    public Message execute(Executable executable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public void setMessage(Message message) {
        if (message instanceof PlayerMove) {
            this.message = (PlayerMove) message;
        }
    }

}
