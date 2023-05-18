package controller;

import dataobjects.DataObject;

public interface ExecutorFactory {

    public Executor createExecutor(DataObject message);
}
