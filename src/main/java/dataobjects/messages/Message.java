package dataobjects.messages;

import java.util.UUID;

public abstract class Message {
    private String messageID;

    public Message() {
        messageID = UUID.randomUUID().toString();
    }

      public String getId() {
        return messageID;
    }

}
