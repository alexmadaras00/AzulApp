package view;

import controller.Mediator;
import messaging.dataobjects.DataObject;
import messaging.executors.Executor;
import messaging.executors.ExecutorFactory;

public class View implements Messager {
    private Mediator mediator;
    private UI userInterface;
    private ExecutorFactory executorFactory;

    public View(ExecutorFactory executorFactory) {
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
