package org.patas.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.patas.events.Event;

import java.util.function.UnaryOperator;

class ControlsPane<E extends Comparable<E>> extends HBox {
    static final double HEIGHT = 50;
    private TextField textField;
    private final Label errorMessage;

    /**
     * Constructs the Controls Pane
     * @param rootPane the root pane that instantiated this class
     */
    ControlsPane(RootPane<E> rootPane, RootPane.ParseFunction<E> parseFunction, UnaryOperator<TextFormatter.Change> textFilter) {
        // Set custom styles to the underlying HBox
        setSpacing(16);
        setAlignment(Pos.CENTER);
        setPrefHeight(HEIGHT);
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        // Initialize buttons and text field
        Button insert = new Button("Insert");
        insert.setOnAction(event -> {
            try {
                rootPane.handleEvent(Event.INSERT, parseFunction.parse(textField.getText()));
            } catch (Exception e) {
                handleEvent(Event.CANT_PARSE, null);
            }
        });
        Button remove = new Button("Remove");
        remove.setOnAction(event -> {
            try {
                rootPane.handleEvent(Event.REMOVE, parseFunction.parse(textField.getText()));
            } catch (Exception e) {
                handleEvent(Event.CANT_PARSE, null);
            }
        });
        textField = new TextField("");
        textField.setTextFormatter(new TextFormatter<>(textFilter));
        textField.setPrefWidth(100);
        textField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                insert.fire();
        });
        errorMessage = new Label("");
        errorMessage.setPrefWidth(240);
        errorMessage.setTextFill(Color.RED);
        getChildren().addAll(insert, remove, textField, errorMessage);
    }

    /**
     * Displays an error message to the user through a red label in
     * controls pane
     * @param event the event to display
     * @param element the element that threw the error
     */
    void handleEvent(Event event, E element) {
        switch (event) {
            case NO_ERROR:
                errorMessage.setText("");
                break;
            case ELEMENT_ALREADY_INSERTED:
                errorMessage.setText("\"" + element + "\" is already in the tree");
                break;
            case ELEMENT_NOT_FOUND:
                errorMessage.setText("\"" + element + "\" isn't in the tree");
                break;
            case CANT_PARSE:
                errorMessage.setText("Couldn't parse that value");
        }
    }
}
