package messaging.messages;

import messaging.dataobjects.PlayerData;

public class OkJoinGame extends Response {
    private PlayerData playerData;

    public OkJoinGame(String requestId) {
        super(requestId);
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

}
