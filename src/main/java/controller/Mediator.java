package controller;

import messaging.dataobjects.DataObject;
import model.Model;
import view.Messager;

public interface Mediator {
    void connectMessager(Messager messager);
    void connectModel(Model model);
    void notify(DataObject message);
}
