package view;

import controller.DataObject;
import controller.IController;

public interface Messager {
    void connectController(IController controller);

    void handleResponse(DataObject message);
}
