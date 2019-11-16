package org.patas.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.patas.Main;

public class ControlsPane extends HBox {
    private Main main;
    private Button insert, remove;
    private TextField numberField;

    /**
     * Constructs the controls pane
     */
    public ControlsPane(Main main) {
        this.main = main;
        // Set custom styles to the underlying HBox
        setSpacing(16);
        setAlignment(Pos.CENTER);
        setPrefHeight(50);
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        // Initialize buttons and text field
        insert = new Button("Insert number");
        insert.setOnAction(event -> dispatchEvent(Events.INSERT, Integer.parseInt(numberField.getText())));
        remove = new Button("Remove number");
        remove.setOnAction(event -> dispatchEvent(Events.REMOVE, Integer.parseInt(numberField.getText())));
        numberField = new TextField("0");
        numberField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("^[0-9]{0,5}$") ? change : null)
        );
        getChildren().addAll(insert, remove, numberField);
    }

    /**
     * Binds width of scene to width of the controls pane instance
     */
    public void init() {
        prefWidthProperty().bind(getScene().widthProperty());
    }

    /**
     * Sends an event to the Application class
     * @param event the type of event to send
     * @param num the number to send
     */
    private void dispatchEvent(Events event, int num) {
        System.out.println(event.name() + " " + num);
        main.relayEvent(event, num);
    }
}
