package org.patas.gui;

import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import org.patas.events.Event;

import java.util.function.UnaryOperator;

public class RootPane<E extends Comparable<E>> extends AnchorPane {
    private final ControlsPane<E> controlsPane;
    private final TreePane<E> treePane;

    /**
     * Builds a RootPane from a function to parse the input and a formatter to limit
     * the characters
     * @param parseFunction the function to parse input
     * @param textFilter the text formatter to apply to the controls pane
     */
    public RootPane(ParseFunction<E> parseFunction, UnaryOperator<TextFormatter.Change> textFilter) {
        controlsPane = new ControlsPane<>(this, parseFunction, textFilter);
        treePane = new TreePane<>(this);
        getChildren().addAll(controlsPane, treePane);
        AnchorPane.setLeftAnchor(controlsPane, 0.0);
        AnchorPane.setTopAnchor(controlsPane, 0.0);
        AnchorPane.setRightAnchor(controlsPane, 0.0);
        AnchorPane.setLeftAnchor(treePane, 0.0);
        AnchorPane.setTopAnchor(treePane, ControlsPane.HEIGHT);
        AnchorPane.setRightAnchor(treePane, 0.0);
        AnchorPane.setBottomAnchor(treePane, 0.0);
    }

    public interface ParseFunction<E> {
        E parse(String str);
    }

    /**
     * Receive the events from controls pane and tree pane and relay
     * them to their corresponding parts
     * @param event the type of event to relay
     * @param element the number to send
     */
    void handleEvent(Event event, E element) {
        switch (event) {
            case INSERT:
            case REMOVE:
                treePane.handleEvent(event, element);
                break;
            case NO_ERROR:
            case ELEMENT_NOT_FOUND:
            case ELEMENT_ALREADY_INSERTED:
                controlsPane.handleEvent(event, element);
                break;
        }
    }
}
