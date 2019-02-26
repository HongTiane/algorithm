package com.zyb.study.algorithm.Linked;

import java.util.Objects;

public class DoublyLinkedList<T> {
    private final Node<T> head = new Node<>();

    private Node<T> tail = head;

    private int length = 0;

    public DoublyLinkedList() {}

    public DoublyLinkedList(final DoublyLinkedList<T> init) {

        Objects.requireNonNull(init);

        Node initCurrentNode = init.head;

        while(initCurrentNode.next != null) {
            initCurrentNode = initCurrentNode.next;

            tail.next = new Node(initCurrentNode.value);
            tail.next.prev = tail;

            tail = tail.next;
        }

        length = init.length;

    }

    public T get(final int index) {
        if(index < 0) {
            throw new IllegalArgumentException();
        }

        if(index >= length) {
            return null;
        }

        Node<T> targetNode = getNode(index);

        return targetNode.value;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public boolean contains(T e) {
        Node<T> currentNode = head;

        while(currentNode.next != null) {
            currentNode = currentNode.next;
            if(currentNode.value.equals(e)) {
                return true;
            }
        }

        return false;
    }

    public int add(final T e) {

        Node<T> newNode = new Node<>(e);

        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        return length++;
    }

    public void insert(final int index, final T e) {

        checkIndex(index);

        Node<T> targetNode = getNode(index);

        Node newNode = new Node(e);

        newNode.next = targetNode;
        newNode.prev = targetNode.prev;

        targetNode.prev.next = targetNode.prev = newNode;

        length++;

    }

    public T delete(final int index) {
        checkIndex(index);

        Node<T> deleteNode = getNode(index);

        deleteNode.prev.next = deleteNode.next;
        deleteNode.next.prev = deleteNode.prev;

        length--;

        return deleteNode.value;
    }

    private void checkIndex(int index) {
        if(index < 0 || index >= length) {
            throw new IllegalArgumentException();
        }
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        for(int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    private class Node<T> {

        public T value;
        public Node<T> next;
        public Node<T> prev;

        public Node() {
        }

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
