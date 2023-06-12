package messaging.messages;

public class NotOkPlayerMove extends Response{
    private String reason;
    
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public NotOkPlayerMove(String requestId) {
        super(requestId);
    }
    
}
