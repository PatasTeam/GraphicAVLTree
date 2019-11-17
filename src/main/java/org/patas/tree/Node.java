package org.patas.tree;

import java.util.ArrayList;

class Node<E extends Comparable<E>> {
    private final E element;
    private int height;
    private Node<E> left, right;

    Node(E element) {
        this.element = element;
        height = 1;
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
