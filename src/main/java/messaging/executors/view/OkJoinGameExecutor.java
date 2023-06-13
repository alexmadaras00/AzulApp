package messaging.executors.view;

import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.messages.Message;
import messaging.messages.OkJoinGame;
import view.UI;

public class OkJoinGameExecutor implements Executor {
    private OkJoinGame message;
    private UI ui;

    @Override
    public Message execute(Executable executable) {
         if (message == null || !(executable instanceof UI)) {
            return null;
        }
        ui = (UI) executable;
        ui.addPlayer(message.getPlayerData().getIdentifier(), message.getPlayerData().getName());
        return null;
    }

    @Override
    public void setMessage(Message message) {
        if (message instanceof OkJoinGame) {
            this.message = (OkJoinGame) message;
        }
    }

}
