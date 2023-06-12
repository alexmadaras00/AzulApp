package messaging.executors.view;

import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.messages.Message;
import messaging.messages.NotOkStartGame;
import view.UI;

public class NotOkStartGameExecutor implements Executor {
    private NotOkStartGame message;
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
        if (message instanceof NotOkStartGame) {
            this.message = (NotOkStartGame) message;
        }
    }

}
