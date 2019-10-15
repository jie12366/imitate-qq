package View;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 熊义杰
 *
 * 所有窗口的父类
 */
public abstract class Window extends Stage {
    Parent root;
    private double xOffset;
    private double yOffset;

    /**
     * 设置图标方法
     */
    public void setIcon(){

        getIcons().add(new Image(getClass().getResourceAsStream("/View/Fxml/CSS/Image/Icon/qq.gif")));

    }

    /**
     * 窗口移动方法
     */
    public void move() {
        root.setOnMousePressed(event -> {
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

    /**
     * 获取root
     *
     * @return
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * 选择界面元素
     *
     * @param id
     * @return
     */
    public Object $(String id) {
        return (Object) root.lookup("#" + id);
    }

}