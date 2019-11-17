package org.patas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.patas.gui.ControlsPane;
import org.patas.gui.TreePane;

public class Main extends Application {
    private ControlsPane controlsPane;
    private TreePane treePane;

    @Override
    public void start(Stage stage) {
        controlsPane = new ControlsPane(this);
        treePane = new TreePane(this);
        stage.setScene(new Scene(new AnchorPane(controlsPane, treePane), 640, 480));
        stage.show();
    }

    /**
     * Receive the events from controls pane and tree pane and relay
     * them to their corresponding parts
     * @param event the type of event to relay
     * @param num the number to send
     */
    public void relayEventFromMain(Event event, int num) {
        // TODO: Remove debugging events
        System.out.println(event.name() + " " + num);
        switch (event) {
            case INSERT:
            case REMOVE:
                treePane.relayEventFromTreePane(event, num);
                break;
            case ELEMENT_NOT_FOUND:
            case ELEMENT_ALREADY_INSERTED:
                controlsPane.setError(event);
                break;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

