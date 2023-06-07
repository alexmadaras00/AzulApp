package dataobjects.executors;

import dataobjects.DataObject;

public interface ExecutorFactory {

    Executor createExecutor(DataObject message);
}
