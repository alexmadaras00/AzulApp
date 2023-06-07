package dataobjects.messages;

import dataobjects.DataObject;
import dataobjects.data.Action;
import dataobjects.data.PlayerData;

public class PlayerMove implements DataObject {
    private PlayerData player;
    private Action action;

    public PlayerMove() {
    }

    public PlayerData getPlayer() {
        return player;
    }

    public void setPlayer(PlayerData player) {
        this.player = player;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerMove)) return false;

        PlayerMove that = (PlayerMove) o;

        if (getPlayer() != null ? !getPlayer().equals(that.getPlayer()) : that.getPlayer() != null) return false;
        return getAction() != null ? getAction().equals(that.getAction()) : that.getAction() == null;
    }

    @Override
    public int hashCode() {
        int result = getPlayer() != null ? getPlayer().hashCode() : 0;
        result = 31 * result + (getAction() != null ? getAction().hashCode() : 0);
        return result;
    }
}
