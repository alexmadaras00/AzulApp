package dataobjects.executors;

import dataobjects.DataObject;
import dataobjects.Executable;

public interface Executor {

    DataObject execute(Executable executable);

}
