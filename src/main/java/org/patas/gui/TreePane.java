package org.patas.gui;

import javafx.scene.layout.Pane;
import org.patas.events.Event;
import org.patas.tree.AVLTree;

public class TreePane<E extends Comparable<E>> extends Pane {
    private final RootPane<E> rootPane;
    private final AVLTree<E> tree;

    /**
     * Constructs the Tree Pane
     * @param rootPane the Application that instantiated this class
     */
    TreePane(RootPane<E> rootPane) {
        this.rootPane = rootPane;
        tree = new AVLTree<>(this);
    }

    /**
     * Sends events from main to underlying AVL Tree and viceversa
     * @param event the type of event to relay
     * @param element the element to send
     */
    public void handleEvent(Event event, E element) {
        rootPane.handleEvent(Event.NO_ERROR, null);
        switch (event) {
            case INSERT:
                tree.insert(element);
                break;
            case REMOVE:
                tree.remove(element);
                break;
            default:
                rootPane.handleEvent(event, element);
        }
    }
}
