package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 熊义杰
 * 好友的主页界面
 */
public class friendPage extends Window{
    public friendPage() throws IOException{
        root = FXMLLoader.load(getClass().getResource("Fxml/friendPage.fxml"));
        setScene(new Scene(root,393,502));
        setTitle("Chat");
        initStyle(StageStyle.TRANSPARENT);
        move();
        quit();
        setIcon();
        alterAction();
    }

    /**
     * 触发修改按钮，进行资料修改
     */
    public void alterAction(){
        ((Button) $("edit")).setOnAction(event -> {
            ((TextField) $("remark")).setEditable(true);
            ((Button) $("submit")).setVisible(true);
            ((Button) $("submit")).setManaged(true);
        });
    }

    /**
     * 设置好友备注
     */
    public void setRemark(String remark){
        ((TextField) $("remark")).setText(remark);
    }

    /**
     * 设置文本框不可编辑，隐藏完成按钮
     */
    public void setNoAction(){
        ((TextField) $("remark")).setEditable(false);
        ((Button) $("submit")).setVisible(false);
        ((Button) $("submit")).setManaged(false);
    }

    /**
     * 设置好友主页的数据
     * @param resultSet 数据结果集
     * @param remark 好友备注
     * @throws SQLException
     */
    public void setFriendPage(ResultSet resultSet,String remark) throws SQLException {
        ((TextField) $("name")).setText(resultSet.getString("name"));
        ((TextField) $("age")).setText(resultSet.getString("age"));
        if(resultSet.getString("sex").equals("man")){
            ((TextField) $("sex")).setText("男");
        }else {
            ((TextField) $("sex")).setText("女");
        }
        ((TextField) $("address")).setText(resultSet.getString("address"));
        ((TextField) $("phone")).setText(resultSet.getString("phone"));
        ((Label) $("account")).setText(resultSet.getString("account"));
        ((TextField) $("label")).setText(resultSet.getString("label"));
        ((TextField) $("remark")).setText(remark);
        setHead(((Button) $("head")),resultSet.getString("head"));
        setBackground((Pane) $("background"),resultSet.getString("background"));
    }

    /**
     * 退出方法
     */
    public void quit(){
        ((Button) $("quit1")).setOnAction(event -> {
            this.close();
        });
    }

    /**
     *设置头像和背景图
     */
    public void setHead(Button button,String head){
        button.setStyle(String.format("-fx-background-image:url('/View/Fxml/CSS/Image/head/%s.jpg')",head));
    }
    public void setBackground(Pane pane, String background){
        pane.setStyle(String.format("-fx-background-image:url('/View/Fxml/CSS/Image/background/%s.jpg')",background));
    }
    /**
     * 设置模态窗口
     * @param window
     */
    public void setModailty(Window window){
        initModality(Modality.APPLICATION_MODAL);
        initOwner(window);
    }
}
