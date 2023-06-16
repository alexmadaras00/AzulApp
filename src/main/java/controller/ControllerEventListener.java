package controller;

import shared.EventType;

public interface ControllerEventListener {
    
    void update(EventType type, String message);
}
