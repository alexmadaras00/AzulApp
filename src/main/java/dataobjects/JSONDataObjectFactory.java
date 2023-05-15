package dataobjects;

import org.json.JSONObject;

public class JSONDataObjectFactory<T extends DataObject> {
    
    public JSONObject toJSON(T object) {
        return null;
    }

    public T fromJSON(JSONObject json) {
        return null;
    }
}