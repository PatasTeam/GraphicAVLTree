package org.patas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patas.gui.RootPane;

/**
 * <h1>Graphic AVL Tree</h1>
 * This program displays an interactive AVL Tree
 *
 * @author  Mario Emilio Jiménez Vizcaíno - A01173359
 * @author  Arturo Efrén Jiménez Garibaldy - A00824428
 * @author  Kevin Torres Martínez - A01656257
 * @version 1.0
 */
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
