package view;

import dataobjects.DataObject;
import controller.Mediator;

public class View implements Messager {
    private Mediator mediator;
    private UI userInterface;

    public View(UI userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void connectMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void notify(DataObject message) {
        userInterface.update(message);
    }

    @Override
    public void send(DataObject message) {
        mediator.notify(message);
    }
}
