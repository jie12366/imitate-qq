package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * 熊义杰
 * 警告信息窗口
 */
public class Alert extends Window{
    public Alert() throws IOException {
        root = FXMLLoader.load(getClass().getResource("Fxml/Alert.fxml"));
        setScene(new Scene(root,351,183));
        initStyle(StageStyle.TRANSPARENT);
        setTitle("Chat");
        move();
        setIcon();
    }

    public void exec(){
        show();
        ((Button) $("submit")).setOnAction(event -> {
           close();
        });
    }
    public void setText(String Text){
        ((Label) $("information")).setText(Text);
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
