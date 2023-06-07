package dataobjects;

import dataobjects.executors.Executor;

public interface ExecutorFactory {

    Executor createExecutor(DataObject message);
}
