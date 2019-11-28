package org.patas.tree;

import org.patas.gui.Connection;
import org.patas.gui.LabeledCircle;
import org.patas.gui.TreePane;

class Node<E extends Comparable<E>> {
    private final E element;
    private Node<E> left, right;
    private int height, nodesToTop, nodesToLeft;
    private final TreePane<E> treePane;
    private final LabeledCircle<E> circle;

    /**
     * Initializes a node from its element, path and parent tree pane
     * @param element the element to display
     * @param treePane the parent tree pane
     */
    Node(E element, TreePane<E> treePane) {
        this.element = element;
        this.treePane = treePane;
        height = 1;
        circle = new LabeledCircle<>(element, treePane);
    }

    /**
     * Generates a node copying another one and setting a new element
     * @param nodeWithNewElement the new element
     * @param oldNode node to copy from
     */
    Node(Node<E> nodeWithNewElement, Node<E> oldNode) {
        element = nodeWithNewElement.getElement();
        treePane = oldNode.treePane;
        left = oldNode.left;
        right = oldNode.right;
        circle = new LabeledCircle<>(element, nodeWithNewElement.circle, treePane);
    }

    E getElement() {
        return element;
    }

    int getHeight() {
        return height;
    }

    /**
     * Recalculates max height from the height of its left and right nodes
     */
    void updateHeight() {
        int heightLeft = left != null ? left.height : 0;
        int heightRight = right != null ? right.height : 0;
        height = Math.max(heightLeft, heightRight) + 1;
    }

    Node<E> getLeft() {
        return left;
    }

    void setLeft(Node<E> left) {
        this.left = left;
    }

    Node<E> getRight() {
        return right;
    }

    void setRight(Node<E> right) {
        this.right = right;
    }

    /**
     * Updates the path from root node to this node
     * @param nodesToTop the number of nodes to the top
     * @param nodesToLeft the number of nodes to the left
     */
    void setPosition(int nodesToTop, int nodesToLeft) {
        this.nodesToTop = nodesToTop;
        this.nodesToLeft = nodesToLeft;
        if (left != null)
            left.setPosition(nodesToTop + 1, 2 * nodesToLeft);
        if (right != null)
            right.setPosition(nodesToTop + 1, 2 * nodesToLeft + 1);
    }

    /**
     * Moves the attached circle to its new position
     * @param totalHeight the total height of the tree
     */
    void renderCircles(int totalHeight) {
        int levelWidth = 1;
        for (int i = 0; i < nodesToTop; i++) levelWidth *= 2;
        circle.adjustSizePosition(levelWidth, totalHeight, nodesToLeft, nodesToTop);
        if (left != null)
            left.renderCircles(totalHeight);
        if (right != null)
            right.renderCircles(totalHeight);
    }

    /**
     * Renders the lines between this circle and its children recursively
     */
    void renderLines() {
        if (left != null) {
            treePane.getChildren().add(new Connection(circle, left.circle));
            left.renderLines();
        }
        if (right != null) {
            treePane.getChildren().add(new Connection(circle, right.circle));
            right.renderLines();
        }
    }

    /**
     * Removes the circle from the parent tree pane before being deleted
     */
    void removeFromTreePane() {
        treePane.getChildren().remove(circle);
    }

    @Override
    public String toString() {
        return element.toString();
    }
}
