package controller;

import org.json.JSONObject;


public interface DataObject {
    JSONObject toJSON();
    DataObject fromJSON(JSONObject object);
}
