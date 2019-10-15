package Model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TuLing {
    public TuLing(){

    }
    public String robot(String str) throws Exception{
        String APIKEY = "4a6e1f87e1674f1289c134bcfc35108d";
        String INFO = URLEncoder.encode(str, "utf-8");
        String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
        URL getUrl = new URL(getURL);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.connect();

        /* 取得输入流，并使用Reader读取*/
        BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        /* 断开连接*/
        connection.disconnect();
        String[] ss = new String[10];
        String s = sb.toString();
        String answer;
        ss = s.split(":");
        answer = ss[ss.length-1];
        answer = answer.substring(1,answer.length()-2);
        return answer;
    }

}
