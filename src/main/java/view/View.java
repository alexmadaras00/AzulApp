package view;

import controller.Mediator;
import messaging.executors.Executor;
import messaging.executors.ExecutorFactory;
import messaging.messages.Message;

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
    public void notify(Message message) {
        Executor executor = executorFactory.createExecutor(message);
        executor.execute(userInterface);
    }

    @Override
    public void send(Message message) {
        mediator.notify(message);
    }

}
