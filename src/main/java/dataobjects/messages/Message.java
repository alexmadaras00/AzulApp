package dataobjects.messages;

import java.util.UUID;

import dataobjects.DataObject;

public abstract class Message implements DataObject {
    private String messageID;

    public Message() {
        messageID = UUID.randomUUID().toString();
    }

    public String getId() {
        return messageID;
    }

}
