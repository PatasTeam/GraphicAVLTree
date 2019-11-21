package org.patas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patas.gui.RootPane;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        RootPane<Integer> root = new RootPane<>(
                Integer::parseInt,
                change -> change.getControlNewText().matches("^[0-9]{0,4}$") ? change : null
        );
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
