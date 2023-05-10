package controller;

import org.json.JSONObject;

public interface IController {
    void notify(Object sender, JSONObject message);
}
