package Main;

import Controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception{
        new Controller().exec();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
