package messaging.executors.view;

import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.messages.Message;
import messaging.messages.NotOkJoinGame;
import view.UI;

public class NotOkJoinGameExecutor implements Executor {
    private NotOkJoinGame message;
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
        if (message instanceof NotOkJoinGame) {
            this.message = (NotOkJoinGame) message;
        }
    }

}
