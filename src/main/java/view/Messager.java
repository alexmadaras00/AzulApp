package view;

import controller.Mediator;
import messaging.dataobjects.DataObject;

public interface Messager {
    void connectMediator(Mediator mediator);
    void connectUI(UI userInterface);
    void send(DataObject message);
    void notify(DataObject message);
}
