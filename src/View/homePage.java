package View;

import Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

/**
 * 熊义杰
 * 个人主页界面
 */
public class homePage extends Window{
    private Vector<MenuItem> rightItem;
    private ContextMenu contextMenu;
    private mainWindow mainwindow;
    private double xOffset;
    private double yOffset;
    public homePage() throws IOException{
        root = FXMLLoader.load(getClass().getResource("Fxml/HomePage.fxml"));
        setScene(new Scene(root,672,544));
        setTitle("Chat");
        initStyle(StageStyle.TRANSPARENT);
        move();
        setIcon();
        ((Button) $("alter")).setTooltip(new Tooltip("编辑资料"));
        alterAction();
        close1();
        mini();
        mainwindow = new mainWindow();
        rightItem = new Vector<>();
        contextMenu = new ContextMenu();
        rightItem.add(new MenuItem("  更换头像  "));
        rightItem.add(new MenuItem("  更换背景  "));
    }

    public void close1(){
        ((Button) $("close")).setOnAction(event -> {
            this.close();
        });
    }
    public void mini(){
        ((Button) $("mini")).setOnAction(event -> {
            setIconified(true);
        });
    }

    /**
     * 重写移动方法，添加右键菜单功能
     */
    public void move(){
        root.setOnMousePressed(event -> {
            contextMenu.getItems().clear();
            if(event.getButton() == MouseButton.SECONDARY){
                rightItem.get(0).setOnAction(event1 -> {
                    String Head = "head" + rand();
                    setHeadPortrait(((Button) $("head")),Head);
                    mainwindow.setHead(Head);
                    try{
                        Controller.database.exec("update user set head = ? where account = ?",Head,((TextField) $("account")).getText());
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                });
                rightItem.get(1).setOnAction(event1 -> {
                    String Background = "background" + rand();
                    setBackground(((Pane) $("background")),"background" + rand());
                    try{
                        Controller.database.exec("update user set background = ? where account = ?",Background,((TextField) $("account")).getText());
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                });
                contextMenu.getItems().addAll(rightItem);
                contextMenu.show(this,event.getScreenX(),event.getScreenY());
            }
            xOffset = getX() - event.getScreenX();
            yOffset = getY() - event.getScreenY();
            getRoot().setCursor(Cursor.CLOSED_HAND);
        });
        root.setOnMouseDragged(event -> {

            setX(event.getScreenX() + xOffset);
            setY(event.getScreenY() + yOffset);


        });
        root.setOnMouseReleased(event -> {
            root.setCursor(Cursor.DEFAULT);
        });
    }

    public int  rand(){
        int i = (int)(Math.random() * 9 + 1);
        return i;
    }

    /**
     * 触发修改按钮，进行资料修改
     */
    public void alterAction(){
        ((Button) $("alter")).setOnAction(event -> {
            ((TextField) $("user")).setEditable(true);
            ((TextField) $("sex")).setEditable(true);
            ((TextField) $("age")).setEditable(true);
            ((TextField) $("nickname")).setEditable(true);
            ((TextField) $("address")).setEditable(true);
            ((TextField) $("phone")).setEditable(true);
            ((TextField) $("label")).setEditable(true);
            ((Button) $("submit")).setVisible(true);
            ((Button) $("submit")).setManaged(true);
        });
    }

    /**
     * 设置文本框不可编辑，隐藏完成按钮
     */
    public void setNoAction(){
        ((TextField) $("user")).setEditable(false);
        ((TextField) $("sex")).setEditable(false);
        ((TextField) $("age")).setEditable(false);
        ((TextField) $("nickname")).setEditable(false);
        ((TextField) $("address")).setEditable(false);
        ((TextField) $("phone")).setEditable(false);
        ((TextField) $("label")).setEditable(false);
        ((Button) $("submit")).setVisible(false);
        ((Button) $("submit")).setManaged(false);
    }

    /**
     * 设置个人主页头像
     * @param button 按钮
     * @param head 头像
     */
    public static void setHeadPortrait(Button button,String head){

        button.setStyle(String.format("-fx-background-image: url('/View/Fxml/CSS/Image/head/%s.jpg')",head));

    }

    /**
     * 设置个人主页背景图片
     * @param pane 按钮
     * @param background 背景图
     */
    public static void setBackground(Pane pane,String background){

        pane.setStyle(String.format("-fx-background-image: url('/View/Fxml/CSS/Image/background/%s.jpg')",background));

    }

    /**
     * 设置个人主页的数据
     * @param userdata 数据库返回的数据键值对集合
     */
    public void setUserData(Map<String,String> userdata){
        ((TextField) $("nickname")).setText(userdata.get("nickname"));
        ((TextField) $("account")).setText(userdata.get("account"));
        ((TextField) $("user")).setText(userdata.get("name"));
        ((TextField) $("sex")).setText(userdata.get("sex"));
        ((TextField) $("birth")).setText(Controller.CAGE.getAge(userdata.get("birthday")) + "岁");
        ((TextField) $("age")).setText(userdata.get("birthday"));
        ((TextField) $("address")).setText(userdata.get("address"));
        ((TextField) $("phone")).setText(userdata.get("phone"));
        ((TextField) $("label")).setText(userdata.get("label"));
        setHeadPortrait(((Button) $("head")),userdata.get("head"));
        setBackground(((Pane) $("background")),userdata.get("background"));
    }

    public void setUserData(ResultSet resultSet){
        try {
            if(resultSet.next()){
                ((TextField) $("nickname")).setText(resultSet.getString("nickname"));
                ((TextField) $("account")).setText(resultSet.getString("account"));
                String Sex = "";
                if(resultSet.getString("sex").equals("man")){
                    Sex = "男";
                }else {
                    Sex = "女";
                }
                ((TextField) $("user")).setText(resultSet.getString("name"));
                ((TextField) $("sex")).setText(Sex);
                LocalDate date = LocalDate.now();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try{
                    Date date1 = sdf.parse(resultSet.getString("birthday"));
                    Instant instant = date1.toInstant();
                    ZoneId zoneId = ZoneId.systemDefault();
                    LocalDate localDate = instant.atZone(zoneId).toLocalDate();
                    int age = localDate.until(date).getYears();
                    String Age = age + "";
                    ((TextField) $("birth")).setText(Age + "岁");
                    ((TextField) $("age")).setText(resultSet.getString("birthday"));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                ((TextField) $("address")).setText(resultSet.getString("address"));
                ((TextField) $("phone")).setText(resultSet.getString("phone"));
                ((TextField) $("label")).setText(resultSet.getString("label"));
                setHeadPortrait(((Button) $("head")),resultSet.getString("head"));
                setBackground(((Pane) $("background")),resultSet.getString("background"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
