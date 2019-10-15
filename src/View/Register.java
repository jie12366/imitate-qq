package View;

import Model.GetMessageCode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * 熊义杰
 * 注册界面
 */
public class Register extends Window {
    private ToggleGroup group;
    public Register() throws IOException {
        root = FXMLLoader.load(getClass().getResource("Fxml/Register.fxml"));
        Scene scene = new Scene(root,600,639);
        initStyle(StageStyle.TRANSPARENT);
        setScene(scene);
        setTitle("Chat");
        group = new ToggleGroup();
        RadioButton radioButton = ((RadioButton) $("man"));
        radioButton.setToggleGroup(group);
        ((RadioButton) $("women")).setToggleGroup(group);
        radioButton.setSelected(true);
        radioButton.requestFocus();
        move();
        setIcon();
    }

    public void setErrorTip(String id,String Text){
        ((Label) $(id)).setText(Text);
    }

    public void resetErrorTip(){
        ((Label) $("accountError")).setText("");
        ((Label) $("nameError")).setText("");
        ((Label) $("passwordError")).setText("");
        ((Label) $("rePasswordError")).setText("");
        ((Label) $("birthError")).setText("");
        ((Label) $("phoneError")).setText("");
        ((Label)$("codeError")).setText("");
    }
    public void clear(){
        ((TextField) $("account")).clear();
        ((TextField) $("name")).clear();
        ((PasswordField) $("password")).clear();
        ((PasswordField) $("rePassword")).clear();
        ((DatePicker) $("birthday")).setValue(null);
        ((TextField) $("phone")).clear();
        RadioButton radioButton = ((RadioButton) $("man"));
        radioButton.setSelected(true);
        radioButton.requestFocus();
    }
    public void setHeadPortrait(Button button,String head){
        button.setStyle(String.format("-fx-background-image:url('View/Fxml/CSS/Image/%s.jpg')",head));
    }
}
