package messaging.messages;

public class NotOkStartGame extends Response {
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public NotOkStartGame(String requestId) {
        super(requestId);
    }

}
