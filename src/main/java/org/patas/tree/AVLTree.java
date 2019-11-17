package org.patas.tree;

import org.patas.Event;
import org.patas.gui.TreePane;

public class AVLTree<E extends Comparable<E>> {
    private final TreePane treePane;
    private Node<E> root;

    public AVLTree(TreePane treePane) {
        this.treePane = treePane;
    }

    /**
     * Inserts an element into the tree by calling the private function inside
     * @param element the element to insert
     */
    public void insert(E element) {
        root = insert(root, element);
        treePane.update();
    }

    /**
     * Inserts an element into the tree while balancing it (private method)
     * @param node the parent node to insert to
     * @param element the element to insert
     * @return the parent node of the modified subtree
     */
    private Node<E> insert(Node<E> node, E element) {
        if (node == null)
            return new Node<>(element);
        int comparison = element.compareTo(node.getElement());
        if (comparison > 0)
            node.setLeft(insert(node.getLeft(), element));
        else if (comparison < 0)
            node.setRight(insert(node.getRight(), element));
        else {
            treePane.relayEventFromTreePane(Event.ELEMENT_ALREADY_INSERTED, 0);
            return node;
        }
        node.updateHeight();
        int balance = balance(node);
        if (balance > 1 && element.compareTo(node.getLeft().getElement()) > 0)
            return simpleRightRotation(node);
        if (balance < -1 && element.compareTo(node.getRight().getElement()) < 0)
            return simpleLeftRotation(node);
        if (balance > 1 && element.compareTo(node.getLeft().getElement()) < 0)
            return doubleRightRotation(node);
        if (balance < -1 && element.compareTo(node.getRight().getElement()) > 0)
            return doubleLeftRotation(node);
        return node;
    }

    /**
     * Removes an element from the tree
     * @param element the element to remove
     */
    public void remove(E element) {
        root = remove(root, element);
        treePane.update();
    }

    /**
     * Removes an element from the tree while balancing it (private method)
     * @param node the parent node to remove from
     * @param element the element to remove
     * @return the parent node of the modified subtree
     */
    private Node<E> remove(Node<E> node, E element) {
        // TODO: Fix this function
        if (node == null) {
            treePane.relayEventFromTreePane(Event.ELEMENT_NOT_FOUND, 0);
            return null;
        }
        int comparison = element.compareTo(node.getElement());
        if (comparison > 0)
            node.setLeft(remove(node.getLeft(), element));
        else if (comparison < 0)
            node.setRight(remove(node.getRight(), element));
        else {
            if (node.getLeft() == null && node.getRight() == null)
                return null;
            else if (node.getLeft() == null)
                node = node.getRight();
            else if (node.getRight() == null)
                node = node.getLeft();
            else {
                Node<E> tempLeft = node.getLeft();
                node = node.getRight();
                Node<E> leftestFromRight = node.getRight();
                while (leftestFromRight.getLeft() != null)
                    leftestFromRight = leftestFromRight.getLeft();
                leftestFromRight.setLeft(tempLeft);
            }
        }
        node.updateHeight();
        int balance = balance(node);
        if (balance > 1 && element.compareTo(node.getLeft().getElement()) > 0)
            return simpleRightRotation(node);
        if (balance < -1 && element.compareTo(node.getRight().getElement()) < 0)
            return simpleLeftRotation(node);
        if (balance > 1 && element.compareTo(node.getLeft().getElement()) < 0)
            return doubleRightRotation(node);
        if (balance < -1 && element.compareTo(node.getRight().getElement()) > 0)
            return doubleLeftRotation(node);
        return node;
    }

    /**
     * Performs a simple right rotation
     * @param parent parent node of unbalanced subtree
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
     * Performs a simple left rotation
     * @param parent parent node of unbalanced subtree
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

    /**
     * Performs a double right rotation
     * @param parent parent node of unbalanced subtree
     * @return new parent node
     */
    private Node<E> doubleRightRotation(Node<E> parent) {
        parent.setLeft(simpleLeftRotation(parent.getLeft()));
        return simpleRightRotation(parent);
    }

    /**
     * Performs a double left rotation
     * @param parent parent node of unbalanced subtree
     * @return new parent node
     */
    private Node<E> doubleLeftRotation(Node<E> parent) {
        parent.setRight(simpleRightRotation(parent.getRight()));
        return simpleLeftRotation(parent);
    }

    /**
     * Method to calculate the balance of a subtree
     * @param node parent node of the subtree
     * @return the difference between the heights of the subtrees
     */
    private int balance(Node<E> node) {
        int heightLeft = node.getLeft() != null ? node.getLeft().getHeight() : 0;
        int heightRight = node.getRight() != null ? node.getRight().getHeight() : 0;
        return heightLeft - heightRight;
    }

    @Override
    public String toString() {
        if (root == null)
            return "Empty tree";
        return String.join("\n", root.printHelper(""));
    }
}
