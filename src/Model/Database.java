package Model;
import java.sql.*;

/**
 * 熊义杰
 * 数据库操控类
 */

public class Database {
    private String url = "jdbc:mysql://120.78.162.121:3306/wechat?serverTimezone=UTC&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true";
    private final static String driver = "com.mysql.jdbc.Driver";
    private String userName = "root"; //数据库账号名
    private String password = "root"; //数据库密码
    private Connection connection;
    private PreparedStatement preparedStatement; //动态查询

    /**
     * 连接数据库
     */
    public void connect(){
        try {
            Class.forName(driver).newInstance();  //动态加载并初始化mysql数据库类
            connection = DriverManager.getConnection(url,userName,password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 实现数据库的动态操作并返回结果集
     *通过调用改方法可以直接动态执行SQL语句
     * 比如   execResult("select * from user where accout = ?",UserName)
     * 可以直接作为参数传入该方法，然后执行。
     * @param Sql
     * @param data
     * @throws SQLException
     * @return
     */
    public ResultSet execResult(String Sql,String... data) throws SQLException{
        preparedStatement = connection.prepareStatement(Sql);
        for(int i = 1; i <= data.length; i++){
            preparedStatement.setString(i, data[i - 1]);
        }
        return preparedStatement.executeQuery();
    }

    /**
     *实现数据库的动态操作并更新信息（不返回数据）
     * 比如   exec("insert into user values(?,?,?),"1","2","3") .....
     * @param Sql
     * @param data
     * @throws SQLException
     */
    public void exec(String Sql,String... data) throws SQLException{
        preparedStatement = connection.prepareStatement(Sql);
        for(int i = 1; i <= data.length; i++){
            preparedStatement.setString(i, data[i - 1]);
        }
        preparedStatement.executeUpdate();
    }

    /**
     * 实现数据库的静态操作
     * 只有sql语句一个参数，没有数据返回也没有数据传入
     * 比如  exec("drop user")
     * @param Sql
     * @throws SQLException
     */
    public void exec(String Sql) throws SQLException{
        preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.executeUpdate();
    }
}
