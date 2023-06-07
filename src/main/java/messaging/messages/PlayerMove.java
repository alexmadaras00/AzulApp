package messaging.messages;

import messaging.dataobjects.Action;
import messaging.dataobjects.PlayerData;

public class PlayerMove extends Request {
    private PlayerData player;
    private Action action;

    public PlayerMove(PlayerData player) {
        super();
        this.player = player;
    }

    public PlayerData getPlayer() {
        return player;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
