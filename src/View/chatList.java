package View;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class chatList {
    private Pane pane;
    private Button head;
    private Label text;
    private Pane left;
    private Pane right;
    private Button arrow;
    private Button p1;
    private Button p3;
    private Button p5;
    private Button p7;
    private Button p2;
    private Button p4;
    private Line l1;
    private Line l2;
    public chatList(){
        l1 = new Line();
        l2 = new Line();
        p1 = new Button();
        p3 = new Button();
        p5 = new Button();
        p7 = new Button();
        p2 = new Button();
        p4 = new Button();
        head = new Button();
        head.setPrefSize(50,50);
        head.getStyleClass().add("head");
        text = new Label();
        text.setPrefSize(300,50);
        text.setWrapText(true);
        left = new Pane();
        left.setPrefSize(400,60);
        left.getStyleClass().add("pane");
        right = new Pane();
        right.setPrefSize(400,60);
        right.getStyleClass().add("pane");
        pane = new Pane();
        pane.setPrefSize(642,70);
        pane.getStyleClass().add("pane");
        arrow = new Button();
        arrow.setPrefSize(32,32);
        arrow.setDisable(false);
    }

    /**
     * 好友的消息气泡和文本设置
     * @param fHead 好友头像
     * @param fText 好友消息
     * @param width 气泡宽度
     * @param height 气泡高度
     * @return pane
     */
    public Pane setLeft(String fHead,String fText,double width,double height){
        text.getStyleClass().add("leftText");
        arrow.getStyleClass().add("leftArrow");
        pane.setPrefHeight(30 + height);
        left.setPrefHeight(30 + height);
        text.setPrefSize(width,height+14);
        head.setLayoutX(10);
        head.setLayoutY(5);
        l1.setStartX(92);
        l1.setStartY(12.5);
        l1.setEndX(81+width);
        l1.setEndY(13.5);
        l2.setStartX(92);
        l2.setStartY(26+height);
        l2.setEndX(85+width);
        l2.setEndY(27+height);
        p2.setPrefSize(42,height-20);
        p4.setPrefSize(42,height-20);
        p1.setPrefSize(42,27);
        p7.setPrefSize(48,26);
        p3.setPrefSize(43,37);
        p5.setPrefSize(42,24);
        p1.setLayoutX(65);
        p1.setLayoutY(5);
        p2.setLayoutX(65);
        p2.setLayoutY(32);
        p3.setLayoutX(75+width);
        p3.setLayoutY(0);
        p4.setLayoutX(80.5+width);
        p4.setLayoutY(33);
        p5.setLayoutX(77+width);
        p5.setLayoutY(11+height);
        p7.setLayoutX(66);
        p7.setLayoutY(7+height);
        p1.getStyleClass().add("cell1");
        int h = (int)(height/20);
        if(h == 2){
            p2.getStyleClass().add("cell11");
            p4.getStyleClass().add("cell33");
        }else if(h == 3){
            p2.getStyleClass().add("cell12");
            p4.getStyleClass().add("cell34");
        }else if(h == 4){
            p2.getStyleClass().add("cell13");
            p4.getStyleClass().add("cell35");
        }
        p3.getStyleClass().add("cell3");
        p5.getStyleClass().add("cell5");
        p7.getStyleClass().add("cell7");
        arrow.setLayoutX(68);
        arrow.setLayoutY(14);
        text.setLayoutX(90);
        text.setLayoutY(12);
        text.setText(fText);
        mainWindow.setHeadPorTrait(head,fHead);
        left.getChildren().add(head);
        /*left.getChildren().add(arrow);*/
        if(height>25){
            left.getChildren().add(p2);
            left.getChildren().add(p4);
        }
        left.getChildren().add(p1);
        left.getChildren().add(p3);
        left.getChildren().add(p5);
        left.getChildren().add(p7);
        left.getChildren().add(text);
        left.getChildren().add(l1);
        left.getChildren().add(l2);
        pane.getChildren().add(left);
        return pane;
    }

    /**
     * 我的消息气泡和文本设置
     * @param iHead 我的头像
     * @param iText 我的消息
     * @param width 气泡宽度
     * @param height 气泡高度
     * @return pane
     */
    public Pane setRight(String iHead,String iText,double width,double height){
        text.getStyleClass().add("rightText");
        arrow.getStyleClass().add("rightArrow");
        pane.setPrefHeight(30 + height);
        right.setPrefHeight(30 + height);
        text.setPrefSize(width,height+18.5);
        l1.setStartX(270 - width);
        l1.setStartY(15);
        l1.setEndX(269);
        l1.setEndY(15.5);
        l2.setStartX(270 - width);
        l2.setStartY(33.2+height);
        l2.setEndX(269);
        l2.setEndY(33.2+height);
        p2.setPrefSize(42,height-20);
        p4.setPrefSize(42,height-20);
        p1.setPrefSize(44,41);
        p7.setPrefSize(38,30);
        p3.setPrefSize(42,41);
        p5.setPrefSize(40,30);
        p1.setLayoutX(233 - width);
        p1.setLayoutY(0);
        p2.setLayoutX(236-width);
        p2.setLayoutY(32);
        p3.setLayoutX(255);
        p3.setLayoutY(0);
        p4.setLayoutX(255.5);
        p4.setLayoutY(32);
        p5.setLayoutX(256.5);
        p5.setLayoutY(7+height);
        p7.setLayoutX(233-width);
        p7.setLayoutY(7+height);
        int h = (int)(height/20);
        if(h == 2){
            p2.getStyleClass().add("cell21");
            p4.getStyleClass().add("cell43");
        }else if(h == 3){
            p2.getStyleClass().add("cell22");
            p4.getStyleClass().add("cell44");
        }else if(h == 4){
            p2.getStyleClass().add("cell23");
            p4.getStyleClass().add("cell45");
        }
        p1.getStyleClass().add("cell2");
        p3.getStyleClass().add("cell6");
        p5.getStyleClass().add("cell8");
        p7.getStyleClass().add("cell4");
        head.setLayoutX(330);
        head.setLayoutY(5);
        arrow.setLayoutX(300);
        arrow.setLayoutY(14);
        text.setLayoutX(270 - width);
        text.setLayoutY(15);
        text.setText(iText);
        mainWindow.setHeadPorTrait(head,iHead);
        right.getChildren().add(head);
        if(height>25){
            right.getChildren().add(p2);
            right.getChildren().add(p4);
        }
        right.getChildren().add(p1);
        right.getChildren().add(p3);
        right.getChildren().add(p5);
        right.getChildren().add(p7);
        right.getChildren().add(text);
        right.getChildren().add(l1);
        right.getChildren().add(l2);
        right.setLayoutX(242);
        pane.getChildren().add(right);
        return pane;
    }
}
