package controller;

import messaging.executors.Executor;
import messaging.executors.ExecutorFactory;
import messaging.messages.Message;
import model.Model;
import view.Messager;

public class Controller implements Mediator {
    private Messager messager;
    private Model model;
    private ExecutorFactory executorFactory;

    public Controller(ExecutorFactory executorFactory) {
        this.executorFactory = executorFactory;
    }

    @Override
    public void connectMessager(Messager messager) {
        this.messager = messager;
    }

    @Override
    public void connectModel(Model model) {
        this.model = model;
    }

    @Override
    public void notify(Message message) {
        Executor executor = executorFactory.createExecutor(message);
        Message newMessage = executor.execute(model);
        if (newMessage != null) {
            messager.notify(newMessage);
        }
    }

}
