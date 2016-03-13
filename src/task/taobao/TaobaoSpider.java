package task.taobao;

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

public class TaobaoSpider extends Spider{

    List<URL> search(String word, int pn) throws IOException, JSONException {
        String path = "http://s.taobao.com/search?";
        String para = "&ajax=true&q=" +  URLEncoder.encode(word, "UTF-8") + "&js=1&style=grid&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20150720&ie=utf8&bcoffset=-4&s=" + 1*(pn-1);
        
        URL taobaoURL = new URL(path + para);
        String json = Network.sendGETRequest(taobaoURL, "utf-8");
        System.out.println(path + para);
//     获取页面的Json信息        
        
        JSONObject object = new JSONObject(json);
        JSONArray auctions = object.getJSONObject("mods").getJSONObject("itemlist").getJSONObject("data").getJSONArray("auctions");
        List<URL> list = new ArrayList<>();

        for (int i = 0; i < auctions.length(); i++) {
            JSONObject tmp = auctions.getJSONObject(i);
            URL detailURL = new URL("http:" +tmp.getString("detail_url"));
            System.out.println(detailURL);
            list.add(detailURL);
        }
        return list;
    }
}
