package view;

import controller.Mediator;
import dataobjects.DataObject;

public interface Messager {
    void connectMediator(Mediator mediator);
    void send(DataObject message);
    void notify(DataObject message);
}
