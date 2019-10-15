package Controller;

import Model.Data.messageData;
import Model.Data.userData;
import Model.*;
import View.Alert;
import View.Dialog;
import View.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * @author 熊义杰
 * 程序的全局操控类
 */
public class Controller {

    public static Dialog dialog;
    private Register register;
    private HeadPorTrait headPorTrait;
    private Forget forget;
    public static userData userdata;
    private homePage homepage;
    private mainWindow mainwindow;
    public static addFriend addfriend;
    public static friendPage friendpage;
    private Alert alert;
    private String codeRegister;
    private String codeForget;
    public static chat ch;
    public static cacuAge CAGE;
    public static Database database;
    public Controller() throws IOException {
        dialog = new Dialog();
        dialogExec();
        dialog.show();
        register = new Register();
        headPorTrait = new HeadPorTrait();
        forget = new Forget();
        database = new Database();
        userdata = new userData();
        homepage = new homePage();
        addfriend = new addFriend();
        friendpage = new friendPage();
        mainwindow = new mainWindow();
        ch = new chat();
        CAGE =new cacuAge();
        alert = new Alert();
        messageData.msg = new Vector<>();
        messageData.msgTip = new HashMap<>();
        messageData.account = new Vector<>();
        database.connect();
        /*如果有记住密码的就把他读到文本框上*/
        try{
            ResultSet ret = database.execResult("select * from save_pass");
            if(ret.next()){
                ResultSet ret1 = database.execResult("select * from dialog");
                while(ret1.next()){
                    if(ret.getString("account").equals(ret1.getString("account"))){
                        if (!ret.next()){
                            return;
                        }
                    }
                }
                ((TextField) $(dialog,"UserName")).setText(ret.getString("account"));
                ((TextField) $(dialog,"Password")).setText(ret.getString("password"));
                setHeadPortrait(((Button) $(dialog,"head")),ret.getString("head"));
                ((Button) $(dialog,"save_pass1")).setVisible(false);
                ((Button) $(dialog,"save_pass1")).setManaged(false);
                ((Button) $(dialog,"save_pass")).setVisible(true);
                ((Button) $(dialog,"save_pass")).setManaged(true);
            }else {
                ((TextField) $(dialog,"UserName")).setText("");
                ((TextField) $(dialog,"Password")).setText("");
                ((Button) $(dialog,"save_pass")).setVisible(false);
                ((Button) $(dialog,"save_pass")).setManaged(false);
                ((Button) $(dialog,"save_pass1")).setVisible(true);
                ((Button) $(dialog,"save_pass1")).setManaged(true);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * 设置头像
     * @param button 按钮
     * @param head 头像
     */
    public void setHeadPortrait(Button button,String head){

        button.setStyle(String.format("-fx-background-image: url('/View/Fxml/CSS/Image/head/%s.jpg')",head));

    }
    /**
     * 实现各个页面的交互，实现最终程序的运行
     */
    public void exec()throws IOException{
        headPorTrait.setModailty(register);  //设置为模态窗口,更换头像时不能操作注册窗口
        alert.setModailty(ch);  //设置为模态窗口,有警告时不能操作主窗口
        friendpage.setModailty(ch); //设置为模态窗口,查看好友资料时不能操作主窗口
        initEvent();
        dialogExec();
        registerExec();
        getForgetCode();
        getRegisterCode();
        searchFriend();
        addFriends();
        forgetExec();
        sendMsg();
        alterPersonPage();
        chatManager.getInstance().setMainwindow(mainwindow);
        changeHead();
    }

    /**
     * 该方法通过页面对象 以及给定的id 选择页面的元素  用法:TextField t = (TextField)$(dialog,"UserName");
     * 这样选出登入框对象的id为UserName的输入框 之后就可以为 t 绑定事件了
     * @param window
     * @param id
     * @return
     */
    private Object $(Window window, String id) {
        return (Object) window.getRoot().lookup("#" + id);
    }

    /**
     * 整个程序事件的初始化
     */
    public void initEvent() {
        ((Button)$(dialog,"register")).setOnAction(event -> {
            dialog.hide();
            dialog.clear();
            register.show();
        });
        ((Button) $(dialog,"getBack")).setOnAction(event -> {
            dialog.hide();
            dialog.clear();
            forget.show();
        });
        ((Button) $(register,"back")).setOnAction(event -> {
            register.hide();
            register.clear();
            dialog.show();
        });
        ((Button) $(register,"ChooseHead")).setOnAction(event -> {
            headPorTrait.show();
        });
        ((Button) $(mainwindow,"myHead")).setOnAction(event -> {
            ((TextField) $(homepage,"address")).setText(userdata.getAddress());
            ((TextField) $(homepage,"account")).setText(userdata.getAccount());
            ((TextField) $(homepage,"label")).setText(userdata.getLabel());
            LocalDate date = LocalDate.now();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date date1 = sdf.parse(userdata.getBirthday());
                Instant instant = date1.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDate localDate = instant.atZone(zoneId).toLocalDate();
                int age = localDate.until(date).getYears();
                String Age = age + "";
                ((TextField) $(homepage,"birth")).setText(Age + "岁");
            }catch (ParseException e){
                e.printStackTrace();
            }
            ((TextField) $(homepage,"phone")).setText(userdata.getPhone());
            ((TextField) $(homepage,"nickname")).setText(userdata.getNickname());
            ((TextField) $(homepage,"user")).setText(userdata.getName());
            ((TextField) $(homepage,"sex")).setText(userdata.getSex());
            homepage.setNoAction();
            homepage.show();
        });
        ((Button) $(mainwindow,"addFriend")).setOnAction(event -> {
            addfriend.show();
        });
        ((Button) $(forget,"cancel")).setOnAction(event -> {
            forget.close();
            dialog.show();
        });
        ((Button) $(mainwindow,"tabMessage")).setOnAction(event -> {
            ((Line) $(mainwindow,"messageLine")).setVisible(true);
            ((Line) $(mainwindow,"ListLine")).setVisible(false);
            ((Line) $(mainwindow,"TrendLine")).setVisible(false);
            ((ListView) $(mainwindow,"message")).setVisible(true);
            ((ListView) $(mainwindow,"message")).setManaged(true);
        });
        ((Button) $(mainwindow,"tabList")).setOnAction(event -> {
            ((Line) $(mainwindow,"messageLine")).setVisible(false);
            ((Line) $(mainwindow,"ListLine")).setVisible(true);
            ((Line) $(mainwindow,"TrendLine")).setVisible(false);
            ((ListView) $(mainwindow,"message")).setVisible(false);
            ((ListView) $(mainwindow,"message")).setManaged(false);
        });
    }

    /**
     * 获取注册验证码
     * @return
     */
    public void getRegisterCode(){
        ((Button) $(register,"code")).setOnAction(event -> {
            String phone = ((TextField) $(register,"phone")).getText();
            try{
                String phoneRegExp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
                if (!phone.equals("") && Pattern.matches(phoneRegExp,phone)){
                    codeRegister = new GetMessageCode().getCode(phone);
                    if (!codeRegister.equals("")){
                        register.setErrorTip("codeError","验证码发送成功");
                    }else {
                        register.setErrorTip("codeError","验证码发送失败，请检查网络");
                    }
                }else {
                    alert.setText("未输入手机号或手机号非法，无法获取验证码");
                    alert.exec();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取忘记密码验证码
     * @return
     */
    public void getForgetCode(){
        ((Button) $(forget,"code")).setOnAction(event -> {
            String phone = ((TextField) $(forget,"phone")).getText();
            try{
                String phoneRegExp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
                if (!phone.equals("") && Pattern.matches(phoneRegExp,phone)){
                    codeForget = new GetMessageCode().getCode(phone);
                    if (!codeForget.equals("")){
                        forget.setErrorTip("phoneError","验证码发送成功");
                    }else {
                        forget.setErrorTip("phoneError","验证码发送失败，请检查网络");
                    }
                }else {
                    alert.setText("未输入手机号或手机号非法，无法获取验证码");
                    alert.exec();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /**
     * 注册界面的运行
     */
    public void registerExec(){
        ((Button) $(register,"register")).setOnAction(event -> {
            register.resetErrorTip();
            String nickname = ((TextField) $(register,"account")).getText();
            String name = ((TextField) $(register,"name")).getText();
            String password = ((PasswordField) $(register,"password")).getText();
            String rePassword = ((PasswordField) $(register,"rePassword")).getText();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = ((DatePicker) $(register,"birthday")).getValue();
            ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
            Date date = Date.from(zdt.toInstant());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String birthday = sdf.format(date);
            String phone = ((TextField) $(register,"phone")).getText();
            String code = ((TextField) $(register,"codeText")).getText();
            String sex;
            RadioButton radioButton = ((RadioButton) $(register,"man"));
            String accountRegExp = "^[0-9,a-z,A-Z,\\u4e00-\\u9fa5]{1,15}$";  //正则匹配：只能输入汉字，数字，字母，长度1-15
            String nameRegExp = "^[a-z,A-Z,\\u4e00-\\u9fa5]{1,100}$";  //正则匹配：只能输入汉字，字母，长度1-100
            String phoneRegExp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$"; //正则匹配：只能是正常的电话号码
            String passwordRegExp = "^[a-z,A-Z,0-9]{6,20}$";  //6-20位的数字或者字母
            String rePasswordRegExp = "^[a-z,A-Z,0-9]{6,20}$";  //6-20位的数字或者字母
            if(nickname.equals("") || password.equals("") || name.equals("") || rePassword.equals("") || birthday.equals("") || phone.equals("") || code.equals("")){
                if(nickname.equals("")){
                    register.setErrorTip("accountError","昵称不能为空！");
                }if(password.equals("")){
                    register.setErrorTip("passwordError","密码不能为空！");
                }if(rePassword.equals("")){
                    register.setErrorTip("rePasswordError","请确认密码！");
                }if(name.equals("")){
                    register.setErrorTip("nameError","姓名不能为空！");
                }if(birthday.equals("")){
                    register.setErrorTip("birthError","生日不能为空！");
                }if(phone.equals("")){
                    register.setErrorTip("phoneError","电话号码不能为空！");
                }if(code.equals("")){
                    register.setErrorTip("phoneError","验证码不能为空！");
                }
            }
            else if(!Pattern.matches(accountRegExp,nickname) || !Pattern.matches(passwordRegExp,password) || !Pattern.matches(rePasswordRegExp,rePassword) || !Pattern.matches(nameRegExp,name) || !Pattern.matches(phoneRegExp,phone)){
                if(!Pattern.matches(accountRegExp,nickname)){
                    register.setErrorTip("accountError","只能输入长度1-15的汉字，数字，字母");
                }if(!Pattern.matches(nameRegExp,name)){
                    register.setErrorTip("nameError","姓名格式错误，只能是汉字或者字母");
                }if(!Pattern.matches(passwordRegExp,password)){
                    register.setErrorTip("passwordError","只能输入长度6-20的数字，字母");
                }if(!Pattern.matches(rePasswordRegExp,rePassword)){
                    register.setErrorTip("rePasswordError","只能输入长度6-20的数字，字母");
                }if(!Pattern.matches(phoneRegExp,phone)){
                    register.setErrorTip("phoneError","电话号码格式不合法");
                }
            }/*else if(!code.equals(codeRegister)){
                register.setErrorTip("codeError","验证码输入错误");
            }*/
            else {
                String account = (int) ((Math.random() * 9 + 1) * 10000000) + "";
                try {
                    ResultSet resultSet = database.execResult("select * from user where account = ?",account);
                    if(!resultSet.next()){
                        if(password.equals(rePassword)){
                            if(radioButton.isSelected()){
                                sex = "man";
                            }else {
                                sex = "woman";
                            }
                            database.exec("insert into user values(?,?,?,?,?,?,?,?,?,?,?)",account,nickname,name,password,birthday,sex,userdata.getHead(),"江西省南昌市","",phone,"background6");
                            database.exec("insert into the_group values(?,?)",account,"我的好友");
                            database.exec("insert into the_group values(?,?)",account,"家人");
                            register.close();
                            register.clear();
                            alert.setText("注册成功，你的账号是" + account);
                            alert.exec();
                            dialog.show();
                        }else {
                            register.setErrorTip("rePasswordError","两次输入的密码不一致，请重新输入！");
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 登录界面的运行
     */
    public void dialogExec(){
        ((Button) $(dialog,"enter")).setOnAction(event -> {
            dialog.resetErrorTip();
            String userName = ((TextField) $(dialog,"UserName")).getText();
            String password = ((PasswordField) $(dialog,"Password")).getText();
            if(userName.equals("") || password.equals("")){
                if(userName.equals("")){
                    dialog.setErrorTip("accountError","未输入账号！");
                }
                if(password.equals("")){
                    dialog.setErrorTip("passwordError","未输入密码！");
                }
            }
            else{
                ResultSet resultSet = null;
                try {
                    resultSet = database.execResult("select * from user where account = ?",userName);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                try {
                    if(resultSet.next()){
                        if(resultSet.getString("password").equals(password)){
                            ResultSet set = database.execResult("select * from dialog where account = ?",userName);
                            if(set.next()){
                                dialog.setErrorTip("accountError","该账号已经登录，不能重复登录！");
                            }else {
                                database.exec("insert into dialog values(?)",userName);
                                /*设置用户的数据*/
                                userdata.setUserData(resultSet);
                                /*记住密码的判断与入库*/
                                if(((Button) $(dialog,"save_pass")).isVisible()){
                                    ResultSet ret2 = database.execResult("select account from save_pass where account =?",userName);
                                    if(!ret2.next()){
                                        database.exec("insert into save_pass values(?,?,?)",userName,password,resultSet.getString("head"));
                                    }
                                }
                                /*如果没有记住密码，遍历save_pass表，如果该账户存在，则删除*/
                                else if(((Button) $(dialog,"save_pass1")).isVisible()){
                                    try{
                                        ResultSet ret = database.execResult("select * from save_pass");
                                        while (ret.next()){
                                            if(ret.getString("account").equals(userName)){
                                                database.exec("delete from save_pass where account =?",userName);
                                            }
                                        }
                                    }catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                }
                                /*设置个人主页的信息*/
                                homepage.setUserData(userdata.getUserData());
                                /*设置主窗口个人信息*/
                                mainwindow.setHead(userdata.getHead());
                                mainwindow.setPersonlPage(userdata.getNickname(),userdata.getLabel());
                                /*设置聊天助手*/
                                try {
                                    mainwindow.addFriend("助手小熊","head9","助手小熊","快来找我聊天吧!");
                                    ((Button) $(Controller.ch,"friendMore")).setText("助手小熊");
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                messageData.msg.add(new Vector<>());
                                messageData.account.add("助手小熊");
                                messageData.msgTip.put("助手小熊",0);
                                /*把所有好友添加到列表*/
                                ResultSet resultSet1 = database.execResult("select * from companion where I_account = ?",userdata.getAccount());
                                while (resultSet1.next()){
                                    ResultSet resultSet2 = database.execResult("select * from user where account = ?",resultSet1.getString("Y_account"));
                                   if(resultSet2.next()){
                                       try {
                                           mainwindow.addFriend(resultSet2.getString("account"),resultSet2.getString("head"),resultSet1.getString("remark"),resultSet2.getString("label"),database,friendpage);
                                           messageData.msg.add(new Vector<>());
                                           messageData.msgTip.put(resultSet2.getString("account"),0);
                                           messageData.account.add(resultSet2.getString("account"));
                                       }catch (IOException e){
                                           e.printStackTrace();
                                       }

                                   }
                                }
                                /*聊天助手一直在线*/
                                mainwindow.getFriendVector().get(0).setOnline();
                                /*获取所有已经登录的好友*/
                                ResultSet resultSet3 = database.execResult("select Y_account from companion where I_account =? and Y_account in (select * from dialog)",userName);
                                while (resultSet3.next()){
                                    int i = messageData.account.indexOf(resultSet3.getString("Y_account"));
                                    if(i != -1){
                                        mainwindow.getFriendVector().get(i).setOnline(); //如果在线就设置上线状态
                                    }
                                }

                                /*把好友分组添加到列表*/
                                ResultSet ret = database.execResult("select * from the_group where account =?",userdata.getAccount());
                                while (ret.next()){
                                    try {
                                        ResultSet ret1 = database.execResult("select * from companion where I_account=? and my_group =?",userdata.getAccount(),ret.getString("my_group"));
                                        int x =0,y =0;
                                        while (ret1.next()){
                                            y++;
                                            ResultSet ret2 = database.execResult("select * from dialog where account=?",ret1.getString("Y_account"));
                                            if(ret2.next()){
                                                x++;
                                            }
                                        }
                                        String num = x + "/" + y;
                                        try{
                                            mainwindow.addGroup(ret.getString("my_group"),num);
                                            mainwindow.addGroupList(ret.getString("my_group"));
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }

                                    }catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                }

                                chatManager.getInstance().connect("127.0.0.1",userName);
                                ResultSet resultSet4 = database.execResult("select * from offlinemsg where Y_account=?",userdata.getAccount());
                                while (resultSet4.next()){
                                    ResultSet resultSet6 = database.execResult("select * from companion where I_account = ?",userdata.getAccount());
                                    while (resultSet6.next()){
                                        if(resultSet6.getString("Y_account").equals(resultSet4.getString("I_account"))){
                                            ResultSet resultSet5 = database.execResult("select * from user where account = ?",resultSet6.getString("Y_account"));
                                            if(resultSet5.next()){
                                                int i = messageData.account.indexOf(resultSet6.getString("Y_account"));
                                                messageData.msg.get(i).add(resultSet6.getString("Y_account") + " " + resultSet4.getString("Msg"));
                                                messageData.msgMap.put(resultSet6.getString("Y_account"),messageData.msg.get(i));
                                                messageData.msgTip.put(resultSet6.getString("Y_account"),1);
                                                mainwindow.getFriendVector().get(i).addMsgTip(1);
                                                database.exec("delete from offlinemsg where Y_account =? and Msg =?",resultSet4.getString("Y_account"),resultSet4.getString("Msg"));
                                            }
                                        }
                                    }
                                }
                                dialog.clear();
                                dialog.close();
                                mainwindow.show();
                            }
                        }else {
                            dialog.setErrorTip("passwordError","你输入的密码错误！");
                        }
                    }else {
                        dialog.setErrorTip("accountError","账号未注册!");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 忘记密码界面的运行
     */
    public void forgetExec(){
        ((Button) $(forget,"reset")).setOnAction(event -> {
            forget.resetErrorTip();
            String account = ((TextField) $(forget,"account")).getText();
            String name = ((TextField) $(forget,"name")).getText();
            String password = ((PasswordField) $(forget,"password")).getText();
            String rePassword = ((PasswordField) $(forget,"rePassword")).getText();
            String phone = ((TextField) $(forget,"phone")).getText();
            String code = ((TextField) $(forget,"codeText")).getText();
            String accountRegExp = "^[0-9,a-z,A-Z,\\u4e00-\\u9fa5]{1,15}$";  //正则匹配：只能输入汉字，数字，字母，长度1-15
            String nameRegExp = "^[a-z,A-Z,\\u4e00-\\u9fa5]{1,100}$";  //正则匹配：只能输入汉字，字母，长度1-100
            String phoneRegExp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$"; //正则匹配：只能是正常的电话号码
            String passwordRegExp = "^[a-z,A-Z,0-9]{6,20}$";  //6-20位的数字或者字母
            String rePasswordRegExp = "^[a-z,A-Z,0-9]{6,20}$";  //6-20位的数字或者字母
            if(account.equals("") || password.equals("") || name.equals("") || rePassword.equals("") || phone.equals("") || code.equals("")){
                if(account.equals("")){
                    forget.setErrorTip("accountError","账号不能为空！");
                }if(password.equals("")){
                    forget.setErrorTip("passwordError","密码不能为空！");
                }if(rePassword.equals("")){
                    forget.setErrorTip("rePasswordError","请确认密码！");
                }if(name.equals("")){
                    forget.setErrorTip("nameError","姓名不能为空！");
                }if(phone.equals("")){
                    forget.setErrorTip("phoneError","电话号码不能为空！");
                }if(code.equals("")){
                    forget.setErrorTip("phoneError","验证码不能为空！");
                }
            }
            else if(!Pattern.matches(accountRegExp,account) || !Pattern.matches(passwordRegExp,password) || !Pattern.matches(rePasswordRegExp,rePassword) || !Pattern.matches(nameRegExp,name) || !Pattern.matches(phoneRegExp,phone)){
                if(!Pattern.matches(accountRegExp,account)){
                    forget.setErrorTip("accountError","只能输入长度1-15的汉字，数字，字母");
                }if(!Pattern.matches(nameRegExp,name)){
                    forget.setErrorTip("nameError","姓名格式错误，只能是汉字或者字母");
                }if(!Pattern.matches(passwordRegExp,password)){
                    forget.setErrorTip("passwordError","只能输入长度6-20的数字，字母");
                }if(!Pattern.matches(rePasswordRegExp,rePassword)){
                    forget.setErrorTip("rePasswordError","只能输入长度6-20的数字，字母");
                }if(!Pattern.matches(phoneRegExp,phone)){
                    forget.setErrorTip("phoneError","电话号码格式不合法");
                }
            }else if (!code.equals(codeForget)){
                forget.setErrorTip("phoneError","验证码错误");
            }
            else {
                try {
                    ResultSet resultSet = database.execResult("select * from user where account = ?",account);
                    if(resultSet.next()){
                        if(resultSet.getString("name").equals(name)){
                            if(resultSet.getString("phone").equals(phone)){
                                if(password.equals(rePassword)){
                                    database.exec("update user set password = ? where account = ?",password,account);
                                    forget.close();
                                    forget.clear();
                                    dialog.show();
                                }else {
                                    forget.setErrorTip("rePasswordError","两次输入密码不一致，请重新输入");
                                }
                            }else {
                                forget.setErrorTip("phoneError","号码不匹配，请重新输入");
                            }
                        }else {
                            forget.setErrorTip("nameError","名字不匹配，请重新输入");
                        }
                    }else {
                        forget.setErrorTip("accountError","该账号不存在，请重新输入");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 发送消息的方法
     */
    public void sendMsg(){
        ((Button) $(ch,"send")).setOnAction(event -> {
            String remark = ((Button) $(ch,"friendMore")).getText();
            String Y_account = "";
            try{
                ResultSet resultSet = database.execResult("select * from companion where remark =? and I_account =?",remark,userdata.getAccount());
                if(resultSet.next() && !remark.equals("助手小熊")){
                    Y_account = resultSet.getString("Y_account");
                }else {
                    Y_account = "助手小熊";
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            String text = ((TextField) $(ch,"input")).getText();
            if(!text.equals("")){
                try{
                    if (Y_account.equals("助手小熊")){
                        try {
                            String xiong = (new TuLing().robot(text)).toString();
                            ch.addRight(userdata.getHead(),text); //我的消息
                            ch.addLeft("head9",xiong); //助手的自动回复消息
                            messageData.msg.get(0).add(userdata.getAccount() + " " + text);
                            messageData.msg.get(0).add(Y_account + " " + xiong);
                            ((TextField) $(ch,"input")).clear(); //清空输入框
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        /*获取已登入的好友*/
                        ResultSet resultSet = database.execResult("select * from dialog where account =?",Y_account);
                        if(resultSet.next()){
                            String line = userdata.getAccount() + " " + Y_account + " " + text;
                            ch.addRight(userdata.getHead(),text);
                            try{
                                chatManager.getInstance().sendServer(line);
                                int i = messageData.account.indexOf(Y_account);
                                if(i != -1){
                                    messageData.msg.get(i).add(userdata.getAccount() + " " + text); //添加到消息集
                                }
                                ((TextField) $(ch,"input")).clear(); //清空输入框
                            }catch (IOException e){
                                alert.setText("你断开了连接！");
                                alert.exec();
                                e.printStackTrace();
                            }
                        }else {
                            ch.addRight(userdata.getHead(),text);
                            database.exec("insert into offlinemsg values(?,?,?)",userdata.getAccount(),Y_account,text);
                            int i = messageData.account.indexOf(Y_account);
                            if(i != -1){
                                messageData.msg.get(i).add(userdata.getAccount() + " " + text); //添加到消息集
                            }
                            ((TextField) $(ch,"input")).clear(); //清空输入框
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else {
                alert.setText("发送的消息不能为空！");
                alert.exec();
            }
        });
    }

    /**
     * 查找好友
     */
    public void searchFriend(){
        ((TextField) $(mainwindow,"search")).setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                String account = ((TextField) $(mainwindow,"search")).getText();
                ((TextField) $(mainwindow,"search")).clear();
                try{
                    ResultSet resultSet = database.execResult("select * from user");
                    while (resultSet.next()){
                        if(resultSet.getString("account").equals(account)){
                            ResultSet resultSet1 = database.execResult("select * from companion where Y_account = ? and I_account = ?",account,userdata.getAccount());
                            if(resultSet1.next()){
                                Controller.ch.friendMore(account,resultSet1.getString("remark"), database,friendpage);
                                ((Button) $(ch,"friendMore")).setText(resultSet1.getString("remark"));
                            }
                            ch.show();
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 查找并添加好友到列表
     */
    public void addFriends(){
        ((TextField) $(addfriend,"textInput")).setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                addfriend.clear();
                String account = ((TextField) $(addfriend,"textInput")).getText();
                ((TextField) $(addfriend,"textInput")).clear();
                if(account.equals("")){
                    alert.setText("您未输入账号！");
                    alert.exec();
                }else if(account.equals(userdata.getAccount())){
                    alert.setText("不能输入你自己的账号！");
                    alert.exec();
                }else {
                    ResultSet resultSet = null;
                    try {
                        resultSet = Controller.database.execResult("select * from user where account = ?",account);
                        boolean flag = false;
                        if (resultSet.next()){
                                try {
                                    addfriend.add(resultSet.getString("head"),resultSet.getString("nickname"),account);
                                    flag = true;
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                        }
                        if(!flag){
                            alert.setText("没有找到相关结果！" );
                            alert.exec();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }else {
                return;
            }
        });
    }

    /**
     * 修改个人信息的方法
     */
    public void alterPersonPage(){
        ((Button) $(homepage,"submit")).setOnAction(event -> {
            String address = ((TextField) $(homepage,"address")).getText();
            String name = ((TextField) $(homepage,"user")).getText();
            String nickname = ((TextField) $(homepage,"nickname")).getText();
            String birth = ((TextField) $(homepage,"age")).getText();
            String label = ((TextField) $(homepage,"label")).getText();
            String phone = ((TextField) $(homepage,"phone")).getText();
            String sex = ((TextField) $(homepage,"sex")).getText();
            String nameRegExp = "^[a-z,A-Z,\\u4e00-\\u9fa5]{1,100}$";  //正则匹配：只能输入汉字，字母，长度1-100
            String phoneRegExp = "^(((13[0-9])|(15[0-3][5-9])|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$"; //正则匹配：只能是正常的电话号码
            if(address.equals("") || phone.equals("") || sex.equals("")){
                if(address.equals("")){
                    alert.setText("地址不能为空");
                    alert.exec();
                }if(sex.equals("")){
                    alert.setText("性别不能为空");
                    alert.exec();
                }if(phone.equals("")){
                    alert.setText("电话号码不能为空");
                    alert.exec();
                }if(name.equals("")){
                    alert.setText("名字不能为空");
                    alert.exec();
                }
            }
            else if(!Pattern.matches(nameRegExp,address) || !Pattern.matches(nameRegExp,label) || !Pattern.matches(phoneRegExp,phone) || (!sex.equals("男") && !sex.equals("女"))){
                if(!Pattern.matches(nameRegExp,address)){
                    alert.setText("格式错误，地址只能是汉字或者字母");
                    alert.exec();
                }if(!Pattern.matches(phoneRegExp,phone)){
                    alert.setText("格式错误，只能是合法的号码");
                    alert.exec();
                }if(!sex.equals("男") && !sex.equals("女")){
                    alert.setText("格式错误，性别只能是男、女");
                    alert.exec();
                }if(!Pattern.matches(nameRegExp,name)){
                    alert.setText("格式错误，名字只能是汉字或者字母");
                    alert.exec();
                }
            }else {
                try {
                    if(sex.equals("男")){
                        sex = "man";
                    }else {
                        sex = "woman";
                    }
                    database.exec("update user set name = ?,nickname = ?,birthday = ?,sex = ?,address = ?,label = ?,phone = ? where account = ?",name,nickname,birth,sex,address,label,phone,userdata.getAccount());
                    ResultSet resultSet = database.execResult("select * from user where account = ?",userdata.getAccount());
                    ResultSet resultSet1 = database.execResult("select * from user where account = ?",userdata.getAccount());
                    if(resultSet.next()){
                        mainwindow.setPersonlPage(resultSet.getString("nickname"),resultSet.getString("label"));
                    }
                    homepage.setUserData(resultSet);
                    if(resultSet1.next()){
                        userdata.setUserData(resultSet1);
                    }
                    homepage.setNoAction();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * 更换头像的方法
     */
    public static void setHeadPorTrait(Button button,String head){
        button.setStyle(String.format("-fx-background-image:url('/View/Fxml/CSS/Image/head/%s.jpg')",head));
    }
    public void changeHead(){
        ((Button) $(headPorTrait,"submit")).setOnAction((event) -> {
            RadioButton one = ((RadioButton)$(headPorTrait,"one"));
            RadioButton two = ((RadioButton)$(headPorTrait,"two"));
            RadioButton three = ((RadioButton)$(headPorTrait,"three"));
            RadioButton four = ((RadioButton)$(headPorTrait,"four"));
            RadioButton five = ((RadioButton)$(headPorTrait,"five"));
            RadioButton six = ((RadioButton)$(headPorTrait,"six"));
            RadioButton seven = ((RadioButton)$(headPorTrait,"seven"));
            RadioButton eight = ((RadioButton)$(headPorTrait,"eight"));
            RadioButton nine = ((RadioButton)$(headPorTrait,"nine"));
            RadioButton ten = ((RadioButton)$(headPorTrait,"ten"));
            if(one.isSelected()){
                userdata.setHead("head1");
            }else if(two.isSelected()){
                userdata.setHead("head2");
            }else if(three.isSelected()){
                userdata.setHead("head3");
            }else if(four.isSelected()){
                userdata.setHead("head4");
            }else if(five.isSelected()){
                userdata.setHead("head5");
            }else if(six.isSelected()){
                userdata.setHead("head6");
            }else if(seven.isSelected()){
                userdata.setHead("head7");
            }else if(eight.isSelected()){
                userdata.setHead("head8");
            }else if(nine.isSelected()){
                userdata.setHead("head9");
            }else if(ten.isSelected()){
                userdata.setHead("head10");
            }
            setHeadPorTrait(((Button) $(register,"HeadPortrait")),userdata.getHead());
            headPorTrait.close();
        });
    }
}
