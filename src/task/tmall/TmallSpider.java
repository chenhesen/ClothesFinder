package task.tmall;

import common.Spider;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import util.Network;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2015/6/2 0002.
 */
public class TmallSpider extends Spider {

    List<URL> search(String word, int pn) throws IOException {
        String path = "http://list.tmall.com/search_product.htm?";
        String para = "type=pc&q=" + URLEncoder.encode(word, "gbk") + "&totalPage=100&sort=s&style=g&from=mallfp..pc_1.1_hq&active=2&jumpto=" + pn;
        System.out.println(path + para);

//      URL tmallURL = new URL(path + para);
//        String html = Network.sendGETRequest(tmallURL, "gbk");
        String html = this.ReadHtmlFile();
        //System.out.println("what");
//        System.out.println(html);

        // 生成document对象
        Document doc = Jsoup.parse(html);
        Elements classContents = doc.getElementsByClass("productTitle");

   //     System.out.println(classContents.html());
        //System.out.println(product.html());

        List<URL> list = new ArrayList<>();
        for(Element e: classContents) {
//            System.out.println(e.text());
            Elements product = e.getElementsByTag("a");
            String productUrl = "http:" + product.attr("href");
            System.out.println(productUrl);
            URL url = new URL(productUrl);
            list.add(url);
        }
        return list;
    }
    
    public String ReadHtmlFile() {
    	File file = new File("C:\\Users\\Administrator\\Desktop\\1.txt");
    	try {
    		String content = FileUtils.readFileToString(file,"utf-8");
            return content; 
    	} catch (IOException e) {
    		e.printStackTrace();
    		return "";
    	}	
    }
}


