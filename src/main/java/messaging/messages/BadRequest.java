package messaging.messages;

public class BadRequest extends Response {
    private String reason;

    public BadRequest(String requestId) {
        super(requestId);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
