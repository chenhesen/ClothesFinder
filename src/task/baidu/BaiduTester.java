package task.baidu;

import util.Helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by root on 2015/6/2 0002.
 */
public class BaiduTester {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("word=");
        String word = reader.readLine();
        System.out.print("startPage=");
        int stPn = Integer.parseInt(reader.readLine());
        System.out.print("endPage=");
        int edPn = Integer.parseInt(reader.readLine());

        BaiduSpider baiduSpider = new BaiduSpider();
        BaiduParser baiduParser = new BaiduParser();

        try {
            for (int pn = stPn; pn <= edPn; ++pn) {
                List<String> list = null;
                list = baiduSpider.search(word, pn);
                for (String json : list) {
                    System.out.println(json);
                    baiduParser.analyzeData(json, Helper.getIdFromJson(json, "currentIndex"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        /*
        for (int pn = startPn; pn <= endPn; ++pn) {
            List<String> jsons = baiduSpider.search(word, rn, pn);
            BaiduParser baiduParser = new BaiduParser();
            baiduParser.analyzeData();
            out.write(resolveImageData(word, rn, pn).toString());
        }
        */
}
