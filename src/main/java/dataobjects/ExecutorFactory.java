package dataobjects;

import dataobjects.executors.Executor;

public interface ExecutorFactory {

    public Executor createExecutor(DataObject message);
}
