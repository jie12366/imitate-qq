package View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * 好友分组
 */
public class FriendGroup {
    private Pane pane;
    private Button group;
    private Button direction;
    private Label label;
    private Label number;
    public FriendGroup(String labels,String Num) throws IOException{
        pane = new Pane();
        pane.setPrefSize(310,30);
        pane.getStyleClass().add("listItem");

        direction = new Button();
        direction.setPrefSize(15,15);
        direction.setLayoutX(5);
        direction.setLayoutY(5);
        direction.getStyleClass().add("dir");
        pane.getChildren().add(direction);

        label = new Label();
        label.setPrefSize(80,20);
        label.setLayoutX(30);
        label.setLayoutY(5);
        label.getStyleClass().add("label");
        pane.getChildren().add(label);

        number = new Label();
        number.setPrefSize(30,20);
        number.setLayoutX(100);
        number.setLayoutY(5);
        number.getStyleClass().add("label");
        pane.getChildren().add(number);

        group = new Button();
        group.setPrefSize(310,30);
        group.setLayoutX(0);
        group.setLayoutY(0);
        group.getStyleClass().add("sendMsg");
        pane.getChildren().add(group);

        setLabel(labels);
        setNumber(Num);
    }
    /**
     * 设置我的分组名
     */
    public void setLabel(String label1){
        label.setText(label1);
    }
    public String getLabel(){
        return label.getText();
    }

    /**
     * 设置分组在线人数与总人数
     */
    public void setNumber(String num){
        number.setText(num);
    }
    public String getNumber(){
        return number.getText();
    }
    public Pane getPane(){
        return pane;
    }
}
