package View;

import Controller.Controller;
import Model.Data.messageData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ApplyAdd extends Window{
    private String Head;
    private mainWindow mWin;
    public ApplyAdd (String head,String nickname,String age,String label) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Fxml/ApplyAdd.fxml"));
        setScene(new Scene(root,422,262));
        initStyle(StageStyle.TRANSPARENT);
        setHead(head);
        setMessage(label);
        setNickname(nickname);
        setRemark(nickname);
        setAge(age);
        move();
        reject();
        Accept();
        setIcon();
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
        setHeadPorTrait(((Button) $("head")),head);
    }
    public void setMessage(String message){
        ((Label) $("message")).setText(message);
    }
    public void setNickname(String Nickname){
        ((Label) $("nickname")).setText(Nickname);
    }
    public void setRemark(String remark){
        ((TextField) $("remark")).setText(remark);
    }
    public void setAge(String age){
        ((Label) $("age")).setText(age);
    }

    public void reject(){
        ((Button) $("cancel")).setOnAction(event -> {
            this.close();
        });
    }
    public void Accept(){
        ((Button) $("submit")).setOnAction(event -> {
            try {
                setFriendToWin();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public void setFriendToWin() throws IOException{
        mWin = new mainWindow();
        String friendName = ((Label) $("nickname")).getText();
        String label = "";
        String account = "";
        String remark = ((TextField) $("remark")).getText();
        String group = ((ComboBox) $("group")).getSelectionModel().getSelectedItem().toString();
        try{
            ResultSet resultSet = Controller.database.execResult("select * from user where nickname =?",friendName);
            label= resultSet.getString("label");
            account = resultSet.getString("account");
        }catch (SQLException e){
            e.printStackTrace();
        }
        int index = addFriend.friendVector.indexOf(friendName);
        if(index != -1){
            Controller.addfriend.getFriendList().getItems().remove(index);
            addFriend.friendVector.remove(index);
        }
        boolean flag1 = false;
        if (!flag1){
            /*添加数据*/
            messageData.msg.add(new Vector<>());
            messageData.account.add(friendName);
            messageData.msgTip.put(friendName,0);
            try {
                Controller.database.exec("insert into companion values(?,?,?,?)",Controller.userdata.getAccount(),account,remark,group);
                try {
                    mWin.addFriend(friendName,Head,remark,label,Controller.database,Controller.friendpage);
                }catch (IOException e){
                    e.printStackTrace();
                }

                /*看好友是否在线，然后设置状态*/
                ResultSet resultSet = Controller.database.execResult("select * from dialog where account = ?",friendName);
                if(resultSet.next()){
                    mWin.getFriendVector().get(messageData.account.size() - 1).setOnline();
                }else {
                    mWin.getFriendVector().get(messageData.account.size() - 1).setOutline();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
