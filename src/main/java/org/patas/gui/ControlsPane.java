package org.patas.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.patas.Event;
import org.patas.Main;

public class ControlsPane extends HBox {
    private final Main main;
    private final Button insert;
    private final Button remove;
    private TextField numberField;
    private final Label errorMessage;

    /**
     * Constructs the Controls Pane
     * @param main the Application that instantiated this class
     */
    public ControlsPane(Main main) {
        this.main = main;
        // Set custom styles to the underlying HBox
        setSpacing(16);
        setAlignment(Pos.CENTER);
        setPrefHeight(50);
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        // Initialize buttons and text field
        insert = new Button("Insert number");
        insert.setOnAction(event -> dispatchEvent(Event.INSERT, Integer.parseInt(numberField.getText())));
        remove = new Button("Remove number");
        remove.setOnAction(event -> dispatchEvent(Event.REMOVE, Integer.parseInt(numberField.getText())));
        numberField = new TextField("0");
        numberField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("^[0-9]{0,5}$") ? change : null)
        );
        errorMessage = new Label("");
        errorMessage.setPrefWidth(320);
        errorMessage.setTextFill(Color.RED);
        getChildren().addAll(insert, remove, numberField, errorMessage);
    }

    /**
     * Sends an event to the Application class
     * @param event the type of event to send
     * @param num the number to send
     */
    private void dispatchEvent(Event event, int num) {
        main.relayEventFromMain(event, num);
    }

    /**
     * Displays an error message to the user through a red label in
     * controls pane
     * @param event the event to display
     */
    public void setError(Event event) {
        switch (event) {
            case ELEMENT_ALREADY_INSERTED:
                errorMessage.setText("That number is already in the tree");
                break;
            case ELEMENT_NOT_FOUND:
                errorMessage.setText("That number isn't in the tree");
                break;
        }
    }
}
