package org.patas.tree;

public class Node<E extends Comparable<E>> {
    private E element;
    private int height;
    private Node<E> left, right;

    Node(E element) {
        this.element = element;
        height = 1;
        left = null;
        right = null;
    }

    int getHeight() {
        return height;
    }

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

    @Override
    public String toString() {
        return element.toString();
    }
}
