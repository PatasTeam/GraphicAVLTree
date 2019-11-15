package org.patas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.patas.gui.ControlsPane;
import org.patas.gui.LabeledCircle;

public class Main extends Application {
    ControlsPane controlsPane;

    @Override
    public void start(Stage stage) {
        controlsPane = new ControlsPane();
        LabeledCircle test = new LabeledCircle("epic", 200, 200, 100);
        Scene scene = new Scene(new Pane(test, controlsPane), 640, 480);
        stage.setScene(scene);
        stage.show();
        controlsPane.init();
    }

    public static void main(String[] args) {
        launch();
    }
}

