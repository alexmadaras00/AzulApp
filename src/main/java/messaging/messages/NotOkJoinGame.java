package messaging.messages;

public class NotOkJoinGame extends Response {
    private String playerName;
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public NotOkJoinGame(String requestId) {
        super(requestId);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
