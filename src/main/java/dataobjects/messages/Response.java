package dataobjects.messages;

public abstract class Response extends Message {
    private String requestId;

    public Response(String requestId) {
        this.requestId = requestId;
    }
    
    public String getRequestId() {
        return requestId;
    }

}
