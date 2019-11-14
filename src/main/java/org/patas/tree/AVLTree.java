package org.patas.tree;

public class AVLTree<E extends Comparable<E>> {
    private Node<E> root;

    public void insert(E element) {
        if (root == null) {
            root = new Node<>(element);
            return;
        }
        // TODO: Finish the function
    }

    // TODO: Add delete function

    /**
     * Method to perform a simple right rotation
     * @param parent parent node of unbalanced part
     * @return new parent node
     */
    private Node<E> simpleRightRotation(Node<E> parent) {
        Node<E> newParent = parent.getLeft();
        Node<E> rightOfNewParent = newParent.getRight();
        newParent.setRight(parent);
        parent.setLeft(rightOfNewParent);
        parent.updateHeight();
        newParent.updateHeight();
        return newParent;
    }

    /**
     * Method to perform a simple left rotation
     * @param parent parent node of unbalanced part
     * @return new parent node
     */
    private Node<E> simpleLeftRotation(Node<E> parent) {
        Node<E> newParent = parent.getRight();
        Node<E> leftOfNewParent = newParent.getLeft();
        newParent.setLeft(parent);
        parent.setRight(leftOfNewParent);
        parent.updateHeight();
        newParent.updateHeight();
        return newParent;
    }

    // TODO: Add double right rotation
    // TODO: Add double left rotation

    /**
     * Method to calculate if a node is a balanced subtree
     * @param node parent node of the subtree
     * @return true if the subtree is balanced
     */
    private boolean isBalanced(Node<E> node) {
        int heightLeft = node.getLeft() != null ? node.getLeft().getHeight() : 0;
        int heightRight = node.getRight() != null ? node.getRight().getHeight() : 0;
        return Math.abs(heightLeft - heightRight) < 2;
    }

    // TODO: Add toString function for debugging
}
