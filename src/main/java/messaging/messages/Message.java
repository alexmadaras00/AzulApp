package messaging.messages;

import java.util.UUID;

import messaging.dataobjects.DataObject;

public abstract class Message implements DataObject {
    private String messageID;

    public Message() {
        messageID = UUID.randomUUID().toString();
    }

    public String getId() {
        return messageID;
    }

}
