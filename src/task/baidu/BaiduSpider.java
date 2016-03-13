package task.baidu;

import common.Spider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Network;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2015/6/2 0002.
 */
public class BaiduSpider extends Spider {
    private int rn = 60;

    public List<String> search(String word, int pn) throws IOException, JSONException {
        String path = "http://image.baidu.com/search/acjson?";
        String para = "tn=resultjson_com&ipn=rj&ie=utf-8&oe=utf-8&word=" + URLEncoder.encode(word, "UTF-8") + "&nc=1&pn=" + pn * rn + "&rn=60";
        System.out.println(path + para);

        URL baiduURL = new URL(path + para);
        String json = Network.sendGETRequest(baiduURL, "utf-8");

        JSONObject object = new JSONObject(json);
        JSONArray data = object.getJSONArray("data");
        List<String> list = new ArrayList<>();
        
        for (int i = 0; i < data.length() - 1; i++) {
            JSONObject tmp = data.getJSONObject(i);
            System.out.println(tmp.getString("objURL"));
            list.add(tmp.getString("objURL"));
        }
        return list;
    }
}
