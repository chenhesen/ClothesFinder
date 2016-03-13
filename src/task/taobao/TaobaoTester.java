package task.taobao;

import org.json.JSONException;
import task.tmall.TmallParser;
import util.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Created by root on 2015/6/2 0002.
 */
public class TaobaoTester {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("word=");
        String word = br.readLine();
        System.out.print("startPage=");
        int stPn = Integer.parseInt(br.readLine());
        System.out.print("endPage=");
        int edPn = Integer.parseInt(br.readLine());

        TaobaoSpider taobaoSpider = new TaobaoSpider();
        TaobaoParser taobaoParser = new TaobaoParser();
        TmallParser tmallParser = new TmallParser();

        for (int pn = stPn; pn <= edPn; ++pn) {
            List<URL> list = null;

            try {
                list = taobaoSpider.search(word, pn);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (URL url : list) {
                switch (url.getHost()) {
                    case "item.taobao.com":
                        taobaoParser.analyzeData(url, Network.getParaFromUrl(url, "id"));
                        break;
                    case "detail.tmall.com":
                        tmallParser.analyzeData(url, Network.getParaFromUrl(url, "id"));
                        break;
                    default:
                        System.out.println("Can't resolve!");
                        break;
                }
            }
        }

    }
        
//        System.out.print("URL=");
//        URL url = new URL(br.readLine());
//
//        if (url.getHost().equals("item.taobao.com")) {
//            TaobaoParser taobaoTask = new TaobaoParser("taobao");
//            taobaoTask.analyzeData(url, Helper.getIdFromUrl(url));
//        } else {
//            System.out.println("Can't resolve!");
//        }
        
}
