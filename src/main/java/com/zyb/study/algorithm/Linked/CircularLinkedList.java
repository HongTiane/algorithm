package com.zyb.study.algorithm.Linked;

import java.util.Objects;

public class CircularLinkedList<T> {
    private final Node<T> head;

    private Node<T> tail;

    private int length = 0;

    public CircularLinkedList() {
        head = new Node<>();
        head.next = head;
        tail = head;
    }

    public CircularLinkedList(final CircularLinkedList<T> init) {
        this();

        Objects.requireNonNull(init);

        Node initCurrentNode = init.head;

        while(initCurrentNode.next != init.head) {
            initCurrentNode = initCurrentNode.next;

            tail.next = new Node(initCurrentNode.value);

            tail = tail.next;
        }

        tail.next = head;

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

        while(currentNode.next != head) {
            currentNode = currentNode.next;
            if(currentNode.value.equals(e)) {
                return true;
            }
        }

        return false;
    }

    public int add(final T e) {

        Node<T> newNode = new Node<>(e, head);

        tail.next = newNode;
        tail = newNode;
        return length++;
    }

    public void insert(final int index, final T e) {

        checkIndex(index);

        Node<T> indexPrevNode = getNode(index - 1);

        Node newNode = new Node(e, indexPrevNode.next);

        indexPrevNode.next = newNode;

        length++;

    }

    public T delete(final int index) {
        checkIndex(index);

        Node<T> indexPrevNode = getNode(index -1);

        Node<T> deleteNode = indexPrevNode.next;
        indexPrevNode.next = deleteNode.next;
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

        public Node() {
        }

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}
