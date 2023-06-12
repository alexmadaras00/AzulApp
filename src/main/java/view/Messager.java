package view;

import controller.Mediator;
import messaging.messages.Message;

public interface Messager {
    void connectMediator(Mediator mediator);
    void connectUI(UI userInterface);
    void send(Message message);
    void notify(Message message);
}
