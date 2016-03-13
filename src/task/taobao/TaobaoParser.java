package task.taobao;

import common.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Network;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 2015/5/28 0028.
 */
public class TaobaoParser extends Parser {

    public TaobaoParser() {
        super("taobao");
    }
    
    public TaobaoParser(String path) {
        super(path);
    }

    public List<String> getDetails(String page) {
        // 存储商品详情
        List<String> list = new ArrayList<>();

        // 生成document对象
        Document doc = Jsoup.parse(page);
        Element title = doc.select("head title").first();
        list.add(title.text());

        Elements attrList = doc.select("ul.attributes-list > li");
        for (Element e : attrList) {
//            System.out.println(e.text());
            list.add(e.text());
        }
        return list;
    }

    public List<URL> getImgs(String page) {
        URL imgPageUrl = getImgPageUrl(page);
        String imgPage = "";
        try {
            imgPage = Network.sendGETRequest(imgPageUrl, "gbk");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String sellerId = getSellerId(page);

        // 整理包含图片连接的js页面
        Document doc = Jsoup.parse(imgPage);
        //System.out.println(doc.html());

        // 存储图片地址ַ
        List<URL> list = new ArrayList<>();
        Elements imgs = doc.select("img[src~=.*?img.*?taobaocdn.*?(png|jpg|gif)]");
        Elements uniqimgs = doc.select("img[src~=.*?img.*?alicdn.*?(png|jpg|gif)]");
        if (imgs.isEmpty()) {
            for (Element uniqimg : uniqimgs) {
               // System.out.println(uniqimg.attr("src"));
                try {
                    list.add(new URL(uniqimg.attr("src")));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            return list;		
		} else {
            for (Element img : imgs) {
               // System.out.println(img.attr("src"));
                try {
                    list.add(new URL(img.attr("src")));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            return list;
		}
    }

    private URL getImgPageUrl(String page) {
        // 获取相关图片页面
        Document doc = Jsoup.parse(page);
        Element script = doc.select("head > script").first();

        final String expImgUrl = "(dsc\\.taobaocdn\\.com[^\"]*?)(\")";
        final Pattern paImgurl = Pattern.compile(expImgUrl);
        final Matcher maImgUrl = paImgurl.matcher(script.data());
        String imgPageUrl = "http://";
        if (maImgUrl.find())
            imgPageUrl += maImgUrl.group(1);
        //System.out.println(imgPageUrl);
        try {
            return new URL(imgPageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private String getSellerId(String page) {
//        Document doc = Jsoup.parse(page);
//        Element id = doc.select("div[data-sellerid]").first();
//        return id.attr("data-sellerid");
//    }
}
