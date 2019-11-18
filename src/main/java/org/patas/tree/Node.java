package org.patas.tree;

import org.patas.gui.LabeledCircle;

import java.util.ArrayList;
import java.util.List;

class Node<E extends Comparable<E>> {
    private final E element;
    private int height;
    private Node<E> left, right;
    private List<Direction> path;
    private LabeledCircle circle;

    private enum Direction { LEFT, RIGHT }

    Node(E element, List<Direction> path) {
        this.element = element;
        this.path = path;
        height = 1;
        circle = new LabeledCircle(element.toString());
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
        if (left == null) return;
        List<Direction> leftPath = new ArrayList<>(path);
        leftPath.add(Direction.LEFT);
        left.setPath(leftPath);
    }

    Node<E> getRight() {
        return right;
    }

    void setRight(Node<E> right) {
        this.right = right;
        if (right == null) return;
        List<Direction> rightPath = new ArrayList<>(path);
        rightPath.add(Direction.RIGHT);
        right.setPath(rightPath);
    }

    /**
     * Updates the path from root node to this node
     * @param path the path to this node
     */
    private void setPath(List<Direction> path) {
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
    public int nodesToTop() {
        return path.size();
    }

    /**
     * Calculates the nodes to the left of this node
     * @return the number of nodes to the left
     */
    public int nodesToLeft() {
        return path.stream()
                .mapToInt(value -> value == Direction.LEFT ? 0 : 1)
                .reduce(0, (acc, elem) -> 2 * acc + elem);
    }

    /**
     * Moves the attached circle to its new position
     */
    public void render() {
        // TODO: Fix how to call this function
//        circle.adjustSizePosition();
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
