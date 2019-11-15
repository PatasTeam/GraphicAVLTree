package org.patas.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;

public class ControlsPane extends HBox {
    private Button insert, remove, search;
    private TextField numberField;

    /**
     * Constructs the controls pane
     */
    public ControlsPane() {
        // TODO: Add event handlers
        insert = new Button("Insert a number");
        remove = new Button("Remove a number");
        search = new Button("Search for a number");
        numberField = new TextField();
        numberField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("^[0-9]*$") ? change : null)
        );
        getChildren().addAll(insert, remove, search, numberField);
    }

    /**
     * Binds width of scene to width of the controls pane instance
     */
    public void init() {
        prefWidthProperty().bind(getScene().widthProperty());
    }

    // TODO: Add functionality
}
