package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Forget extends Window{
    public Forget() throws IOException{
        root = FXMLLoader.load(getClass().getResource("Fxml/Forget.fxml"));
        setScene(new Scene(root,600,600));
        initStyle(StageStyle.TRANSPARENT);
        setTitle("Chat");
        move();
        setIcon();
    }

    /**
     * 设置和重置错误提示
     * @param id 控件
     * @param Text 文本
     */
    public void setErrorTip(String id,String Text){
        ((Label) $(id)).setText(Text);
    }

    public void resetErrorTip(){
        ((Label) $("accountError")).setText("");
        ((Label) $("nameError")).setText("");
        ((Label) $("passwordError")).setText("");
        ((Label) $("rePasswordError")).setText("");
        ((Label) $("phoneError")).setText("");
    }
    public void clear(){
        ((TextField) $("account")).clear();
        ((TextField) $("name")).clear();
        ((PasswordField) $("password")).clear();
        ((PasswordField) $("rePassword")).clear();
        ((TextField) $("phone")).clear();
    }

    public void setHeadPortrait(Button button,String head){
        button.setStyle(String.format("-fx-background-image:url('View/Fxml/CSS/Image/head/%s.jpg')",head));
    }
}