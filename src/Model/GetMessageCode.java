package Model;

import java.util.*;
import org.apache.http.HttpResponse;

/**
 *熊义杰
 * 发送验证码
 */
public class GetMessageCode {
    public GetMessageCode() {

    }
    public String getCode(String phone) throws Exception{
        String host = "https://smsapi.api51.cn";
        String path = "/code/";
        String method = "GET";
        String appcode = "ec92833b98374a6e847ecf7793b76973"; // 自己去申请一个验证码服务，我的额度已经用完了。
        Map<String, String> headers = new HashMap();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        String code = smsCode();
        Map<String, String> querys = new HashMap();
        querys.put("code", code);
        querys.put("mobile", phone);
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        return code;
    }
    // 创建验证码
    public static String smsCode() {
        String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
        return random;
    }
}

