package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;

import java.io.IOException;

/**
 * 熊义杰
 * 更改头像类
 */
public class HeadPorTrait extends Window{
    private ToggleGroup group;
    public HeadPorTrait() throws IOException {
        root = FXMLLoader.load(getClass().getResource("Fxml/headPorTrait.fxml"));
        setScene(new Scene(root,1000,550));
        setResizable(false);
        setTitle("Chat");
        group = new ToggleGroup();
        Group();
        move();
        setIcon();
    }
    public void Group(){
        ((RadioButton) $("one")).setToggleGroup(group);
        ((RadioButton) $("two")).setToggleGroup(group);
        ((RadioButton) $("three")).setToggleGroup(group);
        ((RadioButton) $("four")).setToggleGroup(group);
        ((RadioButton) $("five")).setToggleGroup(group);
        ((RadioButton) $("six")).setToggleGroup(group);
        ((RadioButton) $("seven")).setToggleGroup(group);
        ((RadioButton) $("eight")).setToggleGroup(group);
        ((RadioButton) $("nine")).setToggleGroup(group);
        ((RadioButton) $("ten")).setToggleGroup(group);
        ((RadioButton) $("one")).setSelected(true);
        ((RadioButton) $("one")).requestFocus();
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
