package view;

import controller.DataObject;
import controller.IController;

public class View implements Messager {
    private IController controller;
    private UI userInterface;

    public View(UI userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void connectController(IController controller) {
        this.controller = controller;
        while (true) {
            sendInput(userInterface.getMove());
        }
    }

    @Override
    public void handleResponse(DataObject message) {
        userInterface.update(message);
    }

    private void sendInput(DataObject object) {
        controller.notify(this, object);
    }
}
