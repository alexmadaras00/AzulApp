package messaging.executors;

import messaging.messages.Message;

public interface Executor {
    public Message execute(Executable executable);

    public void setMessage(Message message);
}
