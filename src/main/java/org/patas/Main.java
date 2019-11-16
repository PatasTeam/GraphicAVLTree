package org.patas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.patas.gui.ControlsPane;
import org.patas.gui.Events;
import org.patas.gui.LabeledCircle;
import org.patas.tree.AVLTree;

public class Main extends Application {
    private ControlsPane controlsPane;
    private AVLTree<Integer> tree;

    @Override
    public void start(Stage stage) {
        controlsPane = new ControlsPane(this);
        tree = new AVLTree<>();
        LabeledCircle test = new LabeledCircle("epic", 200, 200, 100);
        Scene scene = new Scene(new Pane(test, controlsPane), 640, 480);
        stage.setScene(scene);
        stage.show();
        controlsPane.init();
    }

    /**
     * Receive the events from controls pane and relay them to backing AVL tree
     * @param event the type of event to relay
     * @param num the number to send
     */
    public void relayEvent(Events event, int num) {
        switch (event) {
            case INSERT:
                tree.insert(num);
                break;
            case REMOVE:
                tree.remove(num);
                break;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

