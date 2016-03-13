package util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by root on 2015/6/2 0002.
 */

  
public class Network {
    public static String sendGETRequest(URL url, String charset) throws IOException {
    	/**
    	**  通过URL获得网页Json数据
    	**/
//        try {
//            Thread.sleep(1000*(int)(2+Math.random()*3));
//            System.out.println("love");
//         } catch (InterruptedException e) { }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
        conn.setRequestProperty("Cookie", "thw=cn; cna=0br0DXHrKAYCAXL/KCwASHcN; v=0; cookie2=1c5def39092f04f6df79151471a0c5d6; t=fdc5b6b5d83bd13a959fb3fd6258a891; JSESSIONID=7D900747052B28783268DB3FB9DB1C85; mt=ci%3D-1_0; l=ApKSSErSlHX-LjpRjFs72KwVYlZ0o5Y9; isg=6476130FD847941A5CDFDC16A91FD03C");
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        while(conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
           // System.out.println(conn.getResponseCode());
            //System.out.println(conn.getHeaderField("location"));
            URL newURL = new URL(conn.getHeaderField("location"));
            conn = (HttpURLConnection)newURL.openConnection();
        }

        //System.out.println(conn.getResponseCode());

        if (conn.getResponseCode() == 200) {
            InputStream in = conn.getInputStream();
            String result = IOUtils.toString(in, charset);
            return (new String(result.getBytes(charset), charset));
        }
        return "";
    }

    
    static public String getParaFromUrl(URL url, String para) {
    	/**
    	**  获得商品ID
    	**/
        String[] queries = url.getQuery().split("\\&");
        for (String query : queries) {
            //System.out.println(query);
            if (query.matches("^" + para + "=.*"))
                return query.split("=")[1];
        }
        return "null";
    }
}
