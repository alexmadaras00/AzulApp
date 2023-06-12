package messaging.executors;

import messaging.messages.Message;

public interface ExecutorFactory {
    Executor createExecutor(Message message);
}
