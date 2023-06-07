package dataobjects.executors;

import dataobjects.DataObject;

public interface Executor {

    DataObject execute(Executable executable);

}
