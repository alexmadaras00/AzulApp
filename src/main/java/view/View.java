package view;

import controller.Controller;

public interface View {
    
    void update();
    void toast(String message);
    void setController(Controller controller);

}
