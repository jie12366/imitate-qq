package Model.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 熊义杰
 * 所有消息（包括好友的和助手的）的数据管理类
 */
public class messageData {
    public static Vector<Vector<String>> msg = new Vector<>(); //保存消息
    public static Map<String,Vector<String>> msgMap = new HashMap<>(); //保存好友消息
    public static Vector<String> account = new Vector<>();  //保存好友账号
    public static Map<String,Integer> msgTip = new HashMap<>(); //保存消息提示和消息数量
}
