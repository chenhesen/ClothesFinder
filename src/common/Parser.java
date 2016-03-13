package common;

import util.Network;
import util.database;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import static java.io.File.separator;

/**
 * Created by root on 2015/5/28 0028.
 */
public abstract class Parser {

    private String path;

    public Parser() {
        this.path = "default";
    }

    public Parser(String destPath) {
        this.path = destPath;
    }

    public String getPath() {
        return this.path;
    }
    
    public void analyzeData(URL url, String destName) throws IOException {

        	String page = Network.sendGETRequest(url, "gbk");
//        	System.out.println(page);
            analyzeData(page, destName);
            
    }

    public void analyzeData(String page, String destName) throws IOException {
            
    	    // 获取页面内容详情
            List<String> details = getDetails(page);
            
//            for (String d : details) {
//                System.out.println(d);
//            }
            
           // 获取相关图片
            List<URL> imgs = getImgs(page);

            
//            for (URL imgUrl : imgs) {
//                System.out.println(imgUrl.toString());
//            }
          
            // 保存数据
            saveData(getPath(), destName, details, imgs);
            
//            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileName+".html"), "gbk");
//            osw.write(html);
//            osw.close();
            
    }

    public void saveData(String path, String name, List<String> details, List<URL> imgs) {
        File file = new File("D:\\Clothes\\" + path  + separator + name + ".txt");
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        if (file.exists())
            return;

        try {
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String line : details) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            String filePath = "D:\\Clothes\\" + path + separator + name + ".txt";
            database.insertFile(name,path,filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File imgFolder = new File("D:\\Clothes\\" + path + separator + name);
        if (!imgFolder.exists())
            imgFolder.mkdirs();

        byte[] buffer = new byte[1024];
        int len;

        for (URL img : imgs) {
            System.out.println("Downloading " + img.toString());
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) img.openConnection();
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36");
                InputStream inputStream = httpURLConnection.getInputStream();
                String[] sp = img.getFile().split("[\\\\/]");
                String imgName = URLDecoder.decode(sp[sp.length - 1], "utf-8");
                FileOutputStream fileOutputStream = new FileOutputStream(new File(imgFolder + separator + imgName));

                while ((len = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, len);
                }
                String imgPath =  imgFolder + separator + imgName;
                fileOutputStream.close();
                inputStream.close();
                database.insertImg(imgName,name,imgPath);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    abstract public List<String> getDetails(String page);

    abstract public List<URL> getImgs(String page);
}
