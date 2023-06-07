package messaging.executors;

import messaging.dataobjects.DataObject;

public interface ExecutorFactory {

    Executor createExecutor(DataObject message);
}
