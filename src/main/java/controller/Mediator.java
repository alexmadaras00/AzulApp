package controller;

import messaging.messages.Message;
import model.Model;
import view.Messager;

public interface Mediator {
    void connectMessager(Messager messager);
    void connectModel(Model model);
    void notify(Message message);
}
