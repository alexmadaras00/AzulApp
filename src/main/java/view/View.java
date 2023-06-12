package view;

import dataobjects.DataObject;
import dataobjects.ExecutorFactory;
import dataobjects.executors.Executor;
import controller.Mediator;

public class View implements Messager {
    private static View instance = null;
    private static ExecutorFactory instanceExecutorFactory = null;
    private Mediator mediator;
    private UI userInterface;
    private ExecutorFactory executorFactory;

    public static void setExecutorFactory(ExecutorFactory executorFactory) {
        instanceExecutorFactory = executorFactory;
    }

    public static View getInstance() {
        if (instance == null) {
            instance = new View(instanceExecutorFactory);
        }
        return instance;
    }

    private View(ExecutorFactory executorFactory) {
        this.executorFactory = executorFactory;
    }

    @Override
    public void connectMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void connectUI(UI userInterface) {
        this.userInterface = userInterface;
        this.userInterface.connectMessager(this);
    }

    @Override
    public void notify(DataObject message) {
        Executor executor = executorFactory.createExecutor(message);
        executor.execute(userInterface);
    }

    @Override
    public void send(DataObject message) {
        mediator.notify(message);
    }

}
