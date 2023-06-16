package view;

import controller.Controller;
import model.ModelProxy;

public interface View {
    
    void update();
    void toast(String message);
    void setModel(ModelProxy model);
    void setController(Controller conroller);

}
