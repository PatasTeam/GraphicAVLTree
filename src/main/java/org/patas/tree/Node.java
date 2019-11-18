package org.patas.tree;

import org.patas.gui.LabeledCircle;
import org.patas.gui.TreePane;

import java.util.ArrayList;
import java.util.List;

class Node<E extends Comparable<E>> {
    private final E element;
    private int height;
    private Node<E> left, right;
    private List<Direction> path;
    private LabeledCircle circle;

    private enum Direction { LEFT, RIGHT }

    Node(E element, List<Direction> path, TreePane treePane) {
        this.element = element;
        this.path = path;
        height = 1;
        circle = new LabeledCircle(element.toString(), treePane);
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
                .mapToInt(value -> value == Direction.LEFT ? 0 : 1)
                .reduce(0, (acc, elem) -> 2 * acc + elem);
    }

    /**
     * Moves the attached circle to its new position
     * @param totalHeight the total height of the tree
     */
    void render(int totalHeight) {
        int levelWidth = 1;
        for (int i = 0; i < nodesToTop(); i++) levelWidth *= 2;
        circle.adjustSizePosition(levelWidth, totalHeight, nodesToLeft(), nodesToTop());
        if (left != null)
            left.render(totalHeight);
        if (right != null)
            right.render(totalHeight);
    }

    ArrayList<String> printHelper(String trailingStr) {
        String newTrailingStr = trailingStr + " ".repeat(toString().length() + 1);
        ArrayList<String> result = new ArrayList<>(
                left != null ? left.printHelper(newTrailingStr) : new ArrayList<>());
        result.add(trailingStr + " " + toString() + (right != null || left != null ? "┤" : ""));
        result.addAll(
                right != null ? right.printHelper(newTrailingStr) : new ArrayList<>());
        return result;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}
