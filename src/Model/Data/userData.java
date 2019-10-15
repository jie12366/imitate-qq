package Model.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 熊义杰
 * 管理数据的类
 */
public class userData {
    private Map<String,String> userMap;   //保存用户数据

    /**
     * 无参构造函数
     */
    public userData(){
        userMap = new HashMap<>();
    }
    /**
     * 有参构造函数，参数为user表的九个字段数据
     * @param account
     * @param name
     * @param password
     * @param age
     * @param sex
     * @param head
     * @param address
     * @param label
     * @param phone
     * @param background
     */
    public userData(String account,String name,String password,int age,String sex,String head,String address,String label,String phone,String background){
        userMap = new HashMap<>();
        userMap.put("account",account);
        userMap.put("name",name);
        userMap.put("password",password);
        userMap.put("age",String.valueOf(age));   //将age转为String型，因为map被定义为Map<String,String>
        userMap.put("sex",sex);
        userMap.put("head",head);
        userMap.put("address",address);
        userMap.put("label",label);
        userMap.put("phone",phone);
        userMap.put("background",background);
    }

    /**
     * 构造函数重载，参数为mysql的resultSet,这是一个包含user表的全部字段的结果集
     * @param resultSet
     * @throws SQLException
     */
    public userData(ResultSet resultSet) throws SQLException {
        userMap = new HashMap<>();
        userMap.put("account",resultSet.getString("account"));
        userMap.put("name",resultSet.getString("name"));
        userMap.put("password",resultSet.getString("password"));
        userMap.put("age",resultSet.getString("age"));
        userMap.put("sex",resultSet.getString("sex"));
        userMap.put("head",resultSet.getString("head"));
        userMap.put("address",resultSet.getString("address"));
        userMap.put("label",resultSet.getString("label"));
        userMap.put("phone",resultSet.getString("phone"));
        userMap.put("background",resultSet.getString("background"));
    }

    /**
     * 设置数据的方法，参数也是结果集
     * @param resultSet
     * @throws SQLException
     */
    public void setUserData(ResultSet resultSet) throws SQLException{
        userMap.put("account",resultSet.getString("account"));
        userMap.put("name",resultSet.getString("name"));
        userMap.put("password",resultSet.getString("password"));
        userMap.put("birthday",resultSet.getString("birthday"));
        if(resultSet.getString("sex").equals("man")){
            userMap.put("sex","男");
        }else {
            userMap.put("sex","女");
        }
        userMap.put("head",resultSet.getString("head"));
        userMap.put("address",resultSet.getString("address"));
        userMap.put("label",resultSet.getString("label"));
        userMap.put("phone",resultSet.getString("phone"));
        userMap.put("background",resultSet.getString("background"));
        userMap.put("nickname",resultSet.getString("nickname"));
    }
    /**
     * 返回user表的数据
     * @return
     */
    public Map<String,String> getUserMap(){
        return userMap;
    }

    /**
     * 类成员的get方法
     * @return
     */
    public Map<String,String> getUserData(){ return userMap;}
    public String getAccount(){
        return userMap.get("account");
    }
    public String getNickname(){
        return userMap.get("nickname");
    }
    public String getName(){
        return userMap.get("name");
    }
    public String getPassword(){
        return userMap.get("password");
    }
    public String getBirthday(){
        return userMap.get("birthday");
    }
    public String getHead(){
        if(userMap.get("head") == null){
            userMap.put("head","head9");
        }
        return userMap.get("head");
    }public String getSex(){
        return userMap.get("sex");
    }public String getAddress(){
        return userMap.get("address");
    }public String getLabel(){
        return userMap.get("label");
    }public String getPhone(){
        return userMap.get("phone");
    }public String getBackground(){
        return userMap.get("background");
    }

    /**
     * 类成员的set方法
     */
    public void setAccount(String account){
        userMap.put("account",account);
    }
    public void setName(String name){
        userMap.put("name",name);
    }
    public void setPassword(String password){
        userMap.put("password",password);
    }
    public void setAge(String age){
        userMap.put("age",age);
    }
    public void setSex(String sex){
        userMap.put("sex",sex);
    }
    public void setHead(String head){
        userMap.put("head",head);
    }
    public void setAddress(String address){
        userMap.put("address",address);
    }
    public void setLabel(String label){
        userMap.put("label",label);
    }
    public void setPhone(String phone){
        userMap.put("phone",phone);
    }
    public void setBackground(String background){
        userMap.put("background",background);
    }
}
