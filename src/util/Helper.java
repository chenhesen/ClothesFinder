package util;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by root on 2015/6/2 0002.
 */
public class Helper {

    static public String getIdFromJson(String jsonObj, String id) {
        JSONObject object = null;
        try {
            object = new JSONObject(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String index = null;
        try {
            index = object.getString(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return index;
    }
}
