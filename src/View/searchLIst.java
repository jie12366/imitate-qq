package View;

import Controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import Model.Data.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class searchLIst {
    private Button head;
    private Alert alert;
    private Label information;
    private Button friend_add;
    private Pane pane;
    private Apply apply;
    private String friendHead;

    /**
     * 构造函数
     * @param ihead 好友头像
     * @param nickname 好友昵称
     */
    public  searchLIst(String ihead,String nickname) throws IOException{
        alert = new Alert();
        head = new Button();
        friend_add = new Button();
        information = new Label();
        pane = new Pane();
        pane.getChildren().add(head);
        pane.getChildren().add(information);
        pane.setPrefSize(470, 60);
        head.setPrefSize(56, 56);
        friend_add.setPrefSize(32, 32);
        friend_add.getStyleClass().add("add");
        friend_add.setLayoutX(400);
        friend_add.setLayoutY(14);
        friend_add.setTooltip(new Tooltip("添加好友"));
        pane.getChildren().add(friend_add);
        head.setLayoutX(2);
        head.setLayoutY(2);
        information.setPrefSize(200, 32);
        information.setLayoutX(65);
        information.setLayoutY(5);
        head.getStyleClass().add("head");
        information.getStyleClass().add("information");
        pane.getStyleClass().add("ListItem");
        setText(nickname);
        setHead(ihead);
        try{
            ResultSet ret = Controller.database.execResult("select * from user where nickname =?",nickname);
            if(ret.next()){
                apply = new Apply(ihead,nickname,Controller.CAGE.getAge(ret.getString("birthday")) + "岁");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
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
    public Pane getPane(){ return pane;}

    /**
     * 发送好友申请
     */
    public void sendAdd(String Account) throws IOException {
        friend_add.setOnAction(event -> {
            ResultSet resultSet1 = null;
            boolean flag = false;
            try {
                resultSet1 = Controller.database.execResult("select * from companion where I_account = ?",Controller.userdata.getAccount());
                while (resultSet1.next()){
                    if(resultSet1.getString("Y_account").equals(Account)){
                        alert.setText("该账号已经是你的好友！");
                        alert.exec();
                        flag = true;
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            if (!flag){
                apply.show();
            }
        });
    }
}
