package org.patas.gui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.patas.Event;
import org.patas.Main;
import org.patas.tree.AVLTree;

public class TreePane extends Pane {
    private final Main main;
    private final AVLTree<Integer> tree;

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
        main.relayEventFromMain(Event.NO_ERROR, 0);
        switch (event) {
            case INSERT:
                tree.insert(num);
                tree.render();
                break;
            case REMOVE:
                tree.remove(num);
                if (tree.isEmpty()) getChildren().clear();
                else tree.render();
                break;
            case INSERT_RANDOM:
                for (int i = 0; i < num; i++)
                    tree.insert((int) (1000 * Math.random()));
                tree.render();
                break;
            default:
                main.relayEventFromMain(event, num);
        }
    }
}
