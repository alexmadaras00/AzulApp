package messaging.executors.view;

import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.messages.Message;
import messaging.messages.OkPlayerMove;

public class OkPlayerMoveExecutor implements Executor {
    private OkPlayerMove message;
    @Override
    public Message execute(Executable executable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public void setMessage(Message message) {
        if (message instanceof OkPlayerMove) {
            this.message = (OkPlayerMove) message;
        }
    }

}
