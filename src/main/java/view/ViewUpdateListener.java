package view;

import controller.ControllerEventListener;
import shared.EventType;

public class ViewUpdateListener implements ControllerEventListener {

    private View view;

    public ViewUpdateListener(View view) {
        this.view = view;
    }

    @Override
    public void update(EventType type, String message) {
        if (type == EventType.UPDATE) {
            view.update();
        }
        if (type == EventType.MESSAGE) {
            view.toast(message);
        }
        
    }

}
