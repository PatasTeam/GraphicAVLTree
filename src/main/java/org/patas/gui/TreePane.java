package org.patas.gui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.patas.Event;
import org.patas.Main;
import org.patas.tree.AVLTree;

public class TreePane extends Pane {
    // Padding for top and bottom, and sides
    static final double[] PADDING = new double[]{ 40, 40 };
    private final Main main;
    private final AVLTree<Integer> tree;
    // TODO: Find a way to show the connections

    /**
     * Constructs the Tree Pane
     * @param main the Application that instantiated this class
     */
    public TreePane(Main main) {
        // Set custom styles
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, ControlsPane.HEIGHT);
        AnchorPane.setRightAnchor(this, 0.0);
        AnchorPane.setBottomAnchor(this, 0.0);
        // Initialize AVL Tree
        tree = new AVLTree<>(this);
        this.main = main;
    }

    /**
     * Sends events from main to underlying AVL Tree and viceversa
     * @param event the type of event to relay
     * @param num the number to send
     */
    public void relayEventFromTreePane(Event event, int num) {
        switch (event) {
            case INSERT:
                tree.insert(num);
                break;
            case REMOVE:
                tree.remove(num);
                break;
            default:
                main.relayEventFromMain(event, num);
        }
    }
}
