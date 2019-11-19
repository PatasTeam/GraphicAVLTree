package org.patas.tree;

import org.patas.gui.Connection;
import org.patas.gui.LabeledCircle;
import org.patas.gui.TreePane;

import java.util.ArrayList;
import java.util.List;

class Node<E extends Comparable<E>> {
    private final E element;
    private int height;
    private TreePane treePane;
    private Node<E> left, right;
    private List<Direction> path;
    private LabeledCircle circle;

    private enum Direction { LEFT, RIGHT }

    /**
     * Initializes a node from its element, path and parent tree pane
     * @param element the element to display
     * @param treePane the parent tree pane
     */
    Node(E element, TreePane treePane) {
        this.element = element;
        this.treePane = treePane;
        path = new ArrayList<>();
        height = 1;
        circle = new LabeledCircle(element.toString(), treePane);
    }

    /**
     * Generates a node copying another one and setting a new element
     * @param oldNode node to copy from
     * @param element the new element
     */
    Node(Node<E> oldNode, E element) {
        this.element = element;
        treePane = oldNode.treePane;
        path = oldNode.path;
        left = oldNode.left;
        right = oldNode.right;
        oldNode.removeFromTreePane();
        circle = new LabeledCircle(element.toString(), oldNode.circle.getCircleColor(), treePane);
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
        int heightLeft = left != null ? left.getHeight() : 0;
        int heightRight = right != null ? right.getHeight() : 0;
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
     * @param path the path to this node
     */
    void setPath(List<Direction> path) {
        this.path = path;
        if (left != null) {
            List<Direction> leftPath = new ArrayList<>(path);
            leftPath.add(Direction.LEFT);
            left.setPath(leftPath);
        }
        if (right != null) {
            List<Direction> rightPath = new ArrayList<>(path);
            rightPath.add(Direction.RIGHT);
            right.setPath(rightPath);
        }
    }

    /**
     * Calculates the nodes to the root of the tree
     * @return the number of nodes to the top
     */
    private int nodesToTop() {
        return path.size();
    }

    /**
     * Calculates the nodes to the left of this node
     * @return the number of nodes to the left
     */
    private int nodesToLeft() {
        return path.stream()
                .mapToInt(value -> value == Direction.RIGHT ? 0 : 1)
                .reduce(0, (acc, elem) -> 2 * acc + elem);
    }

    /**
     * Moves the attached circle to its new position
     * @param totalHeight the total height of the tree
     */
    void renderCircles(int totalHeight) {
        int levelWidth = 1;
        for (int i = 0; i < nodesToTop(); i++) levelWidth *= 2;
        circle.adjustSizePosition(levelWidth, totalHeight, nodesToLeft(), nodesToTop());
        if (left != null)
            left.renderCircles(totalHeight);
        if (right != null)
            right.renderCircles(totalHeight);
    }

    /**
     * Renders the lines between the circles recursively
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
