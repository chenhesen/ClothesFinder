package task.tmall;

import util.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;


/**
 * Created by root on 2015/6/2 0002.
 */
public class TmallTester {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("word=");
        String word = br.readLine();
        System.out.print("startPage=");
        int stPn = Integer.parseInt(br.readLine());
        System.out.print("endPage=");
        int edPn = Integer.parseInt(br.readLine());

        TmallSpider tmallSpider = new TmallSpider();
        TmallParser tmallParser = new TmallParser();
        
        for (int pn = stPn; pn <= edPn; ++pn) {
            List<URL> list = null;
            
            list = tmallSpider.search(word, pn);

            for (URL url : list) {
            	
                tmallParser.analyzeData(url, Network.getParaFromUrl(url, "id"));
            }
        }
    }
}
