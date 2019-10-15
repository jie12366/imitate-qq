package View;

import Controller.Controller;
import Model.Data.messageData;
import Model.Database;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

public class MyFriend {
    private Alert alert;
    private Button head;  //好友头像
    public Label information; //信息属性，用来设置好友备注显示
    public Label label;  //个签
    private Pane pane;  //总容器
    private Button send;   //右键点击的按钮
    private String friendHead;
    private Button MsgTip;  //消息提示
    private Button state;   //在线状态
    private String friendName; //好友账号
    Vector<MenuItem> items;  //右键菜单

    /**
     * 类的构造方法
     * @param fHead 好友头像
     * @param fAccount 好友账号
     * @param remark 好友备注
     * @param label2 好友个签
     * @throws IOException
     */
    public MyFriend(String fHead,String fAccount,String remark,String label2) throws IOException {
        pane = new Pane();
        pane.setPrefSize(310,50);
        pane.getStyleClass().add("listItem");
        alert = new Alert();
        head = new Button();
        head.setPrefSize(46,46);
        head.setLayoutX(2);
        head.setLayoutY(2);
        head.getStyleClass().add("head");
        pane.getChildren().add(head);

        information = new Label();
        information.setPrefSize(120,30);
        information.setLayoutX(55);
        information.setLayoutY(5);
        information.getStyleClass().add("information");
        pane.getChildren().add(information);

        label = new Label();
        label.setPrefSize(200,20);
        label.setLayoutX(55);
        label.setLayoutY(30);
        label.getStyleClass().add("label");
        pane.getChildren().add(label);

        send = new Button();
        send.setPrefSize(310,50);
        send.setLayoutY(0);
        send.setLayoutX(0);
        send.getStyleClass().add("sendMsg");
        pane.getChildren().add(send);

        MsgTip = new Button();
        MsgTip.setPrefSize(17,17);
        MsgTip.setLayoutX(280);
        MsgTip.setLayoutY(34);
        MsgTip.getStyleClass().add("no-MsgTip");
        pane.getChildren().add(MsgTip);

        state = new Button();
        state.setPrefSize(15,15);
        state.setLayoutX(33);
        state.setLayoutY(32);
        pane.getChildren().add(state);
        state.getStyleClass().add("outline");

        items = new Vector<>();
        items.add(new MenuItem("标为未读"));
        items.add(new MenuItem("好友资料"));
        items.add(new MenuItem("清除聊天记录"));
        items.add(new MenuItem("删除好友"));
        friendName = fAccount;
        setText(remark);
        setLabel(label2);
        setHead(fHead);
        setMenuList();
    }

    /**
     * 设置头像的方法
     * @param button
     * @param head
     */
    public void setHeadPorTrait(Button button, String head) {
        button.setStyle(String.format("-fx-background-image:url('View/Fxml/CSS/Image/head/%s.jpg')", head));
    }
    public void setHead(String head){
        setHeadPorTrait(this.head,head);
        friendHead = head;
    }

    /**
     * 设置与获取好友列表上的备注
     */
    public void setText(String Text){
        information.setText(Text);
    }
    public String getText(){ return information.getText();}

    /**
     * 设置个签或最近消息
     */
    public void setLabel(String label1){
        label.setText(label1);
    }
    public String  getLabel(){
        return label.getText();
    }

    /**
     * 一波get，set方法
     */
    public Pane getPane(){ return pane;}
    public Button getHead() {
        return head;
    }

    /**
     * 好友列表的右键菜单
     */
    public void setMenuList(){
        ContextMenu menu = new ContextMenu();
        for(int i=0;i<items.size();i++){
            menu.getItems().add(items.get(i));
        }
        send.setContextMenu(menu);  //设置右键按钮菜单
    }

    /**
     * 消息提示设置，并设置消息数量
     * @param value 消息数量
     */
    public void addMsgTip(int value){
        MsgTip.getStyleClass().clear();
        MsgTip.getStyleClass().add("MsgTip");
        if (!MsgTip.getText().equals("")){
            value = value + Integer.valueOf(MsgTip.getText());
        }
        MsgTip.setText(String.valueOf(value));
    }

    /**
     * 消除消息提示，设置为空
     */
    public void clearMsgTip(){
        MsgTip.getStyleClass().clear();
        MsgTip.getStyleClass().add("no-MsgTip");
        if(!MsgTip.getText().equals("")){
            MsgTip.setText("");
        }
    }

    /**
     * 设置上线状态
     */
    public void setOnline(){
        state.getStyleClass().clear();
        state.getStyleClass().add("online");
    }

    /**
     * 设置下线状态
     */
    public void setOutline(){
        state.getStyleClass().clear();
        state.getStyleClass().add("outline");
    }

    /**
     * @return 获取好友在线状态
     */
    public boolean getState(){
        if (state.getStyleClass().equals("online")) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 将消息设置为已读或者未读
     */
    public void setActionForMsgTip(){
        if(MsgTip.getStyleClass().equals("no-MsgTip")){
            items.get(0).setText("标为未读");
        }else if(MsgTip.getStyleClass().equals("MsgTip")){
            items.get(0).setText("标为已读");
        }
        items.get(0).setOnAction(event -> {
            if(items.get(0).equals("标为已读")){
                MsgTip.getStyleClass().clear();
                MsgTip.getStyleClass().add("no-MsgTip");
                clearMsgTip();
                items.get(0).setText("标为未读");
            }else {
                MsgTip.getStyleClass().clear();
                MsgTip.getStyleClass().add("MsgTip");
                addMsgTip(1);
                items.get(0).setText("标为已读");
            }
        });
    }

    /**
     * 绑定窗口事件的方法
     * @param window
     * @param id
     * @return
     */
    private Object $(Window window, String id) {
        return (Object) window.getRoot().lookup("#" + id);
    }

    /**
     * 选定好友的方法
     * @param mainwindow 调用主窗口方法
     * @param fRemark 好友账号
     * @param head 我的头像
     */
    public void setActionForSend(mainWindow mainwindow,String fRemark,String head){
        send.setOnAction(event -> {
            String friendAccount = friendName;
            ((Button) $(Controller.ch,"friendMore")).setText(information.getText());
            Controller.ch.friendMore(fRemark,information.getText(), Controller.database,Controller.friendpage);
            /*获取当前好友的位置*/
            int index = messageData.account.indexOf(friendAccount);
            if(index != -1){
                mainwindow.getFriendlist().getSelectionModel().select(index); //选中
                this.clearMsgTip(); //清除消息提示
                messageData.msgTip.put(friendAccount,0);
                Controller.ch.show();
            }
            /*生成消息映射*/
            for(int i=0; i<messageData.account.size(); i++){
                messageData.msgMap.put(messageData.account.get(i),messageData.msg.get(i));
            }
            /*遍历消息映射,用entry方法遍历map*/
            for (Map.Entry<String,Vector<String>> entry : messageData.msgMap.entrySet()){
                if(entry.getKey().equals(friendAccount)){
                    ((ListView) $(Controller.ch,"ChatList")).getItems().clear();  //先清屏
                    Vector<String> record = entry.getValue();  //获取消息记录
                    for (int i=0;i<record.size();i++){
                        String []curren = record.get(i).split(" ");
                        String account = curren[0];
                        String Msg = "";
                        for (int j=1;j<curren.length;j++){
                            Msg = Msg + curren[j] + " ";
                        }
                        if(account.equals(fRemark)){
                            Controller.ch.addLeft(friendHead,Msg);
                        }else {
                            Controller.ch.addRight(head,Msg);
                        }
                    }
                }
            }
        });
    }

    /**
     * 修改好友备注
     */
    public void editFriendRemark(friendPage friendpage, Database database, String account, String Account){
        ((Button) $(friendpage,"submit")).setOnAction(event -> {
            String remark = ((TextField) $(friendpage,"remark")).getText();
            String RegExp = "^[a-z,A-Z,\\u4e00-\\u9fa5]{1,100}$";
            if(!Pattern.matches(RegExp,remark)){
                alert.setText("备注不合法，只能是汉字和字母");
                alert.exec();
            }else {
                try {
                    database.exec("update companion set remark = ? where Y_account = ? and I_account =?",remark,account,Account);
                    friendpage.setRemark(remark);
                    information.setText(remark);
                    friendpage.setNoAction();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 查看好友资料
     * @param database 调用数据库
     * @param friendpage 好友主页
     * @param account 好友账号
     */
    public void setActionForFriendPage(Database database,friendPage friendpage,String account,String Account){
        items.get(1).setOnAction(event -> {
            if(friendpage.isShowing()){
                friendpage.close();
            }
            try{
                ResultSet resultSet = database.execResult("select * from user where account = ?",account);
                if(resultSet.next()){
                    friendpage.setFriendPage(resultSet,information.getText());
                    editFriendRemark(friendpage,database,account,Account);
                    friendpage.setNoAction();
                    friendpage.show();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }

    /*清除聊天记录*/
    public void clearMsg(mainWindow mainwindow){
        items.get(2).setOnAction(event -> {
            int index = messageData.account.indexOf(friendName);
            if(index!=-1){
                int index1 = mainwindow.getFriendlist().getSelectionModel().getSelectedIndex();
                messageData.msg.get(index).clear();
                messageData.msgTip.remove(friendName);
                messageData.msgMap.remove(friendName);
                if(index == index1){
                    ((ListView)Controller.ch.$("ChatList")).getItems().clear();
                }
            }
        });
    }

    /**
     * 删除列表好友
     * @param database
     * @param mainwindow
     * @param account 我的账号
     */
    public void removeFriend(Database database,mainWindow mainwindow,String account){
        items.get(3).setOnAction(event -> {
            int i = messageData.account.indexOf(friendName);
            if(i != -1){
                if(i == 0){
                    alert.setText("这是助手，不能删除！");
                    alert.exec();
                }else {
                    mainwindow.getFriendVector().remove(i);
                    ((ListView) $(mainwindow,"message")).getItems().remove(i);
                    messageData.account.remove(i);
                    messageData.msg.remove(i);
                    messageData.msgTip.remove(friendName);
                    try{
                        database.exec("delete from companion where I_account =? and Y_account=?",account,friendName);
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
