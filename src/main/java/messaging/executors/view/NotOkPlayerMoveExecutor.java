package messaging.executors.view;

import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.messages.Message;
import messaging.messages.NotOkPlayerMove;
import view.UI;

public class NotOkPlayerMoveExecutor implements Executor {
    private NotOkPlayerMove message;
    private UI ui;

    @Override
    public Message execute(Executable executable) {
        if (message == null || !(executable instanceof UI)) {
            return null;
        }
        ui = (UI) executable;
        return null;
    }

    @Override
    public void setMessage(Message message) {
        if (message instanceof NotOkPlayerMove) {
            this.message = (NotOkPlayerMove) message;
        }
    }

}
