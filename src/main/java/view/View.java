package view;

import controller.DataObject;
import controller.IController;

public class View implements Messager {
    private IController controller;
    private UI userInterface;

    @Override
    public void connectController(IController IController) {

    }

    private void sendInput(DataObject object) {

    }
}
