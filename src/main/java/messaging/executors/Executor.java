package messaging.executors;

import messaging.messages.Message;

public interface Executor {
    Message execute(Executable executable);

    void setMessage(Message message);
}
