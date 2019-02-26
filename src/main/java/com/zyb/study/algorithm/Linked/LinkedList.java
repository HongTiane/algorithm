package com.zyb.study.algorithm.Linked;

import java.util.Objects;

public class LinkedList<T> {

    private final Node<T> head = new Node<>();

    private Node<T> tail = head;

    private int length = 0;

    public LinkedList() {}

    public LinkedList(final LinkedList<T> init) {

        Objects.requireNonNull(init);

        Node initCurrentNode = init.head;

        while(initCurrentNode.next != null) {
            initCurrentNode = initCurrentNode.next;

            tail.next = new Node(initCurrentNode.value);

            tail = tail.next;
        }

        length = init.length;

    }

    public T get(final int index) {
        if(index < 0) {
            throw new IndexOutOfBoundsException("Index: "+index);
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

    public int length() {
        return length;
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
        tail = newNode;
        return length++;
    }

    public void insert(final int index, final T e) {

        if(index < 0 || index > length) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }

        Node<T> indexPrevNode = getNodeIncludeHead(index);

        Node newNode = new Node(e, indexPrevNode.next);

        indexPrevNode.next = newNode;

        length++;

    }

    public T remove(final int index) {
        if(index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }

        Node<T> indexPrevNode = getNodeIncludeHead(index);

        Node<T> deleteNode = indexPrevNode.next;
        indexPrevNode.next = deleteNode.next;
        length--;

        return deleteNode.value;
    }

    public boolean remove(final T t) {
        Node<T> currentNode = head;

        while(currentNode.next != null) {
            if(currentNode.next.value.equals(t)) {
                Node<T> deleteNode = currentNode.next;
                currentNode.next = deleteNode.next;
                length--;
                return true;
            }
            currentNode = currentNode.next;
        }

        return false;
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        for(int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    private Node<T> getNodeIncludeHead(int index) {
        Node<T> currentNode = head;
        for(int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    @Override
    public String toString() {
        if(length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> currentNode = head;
        if(length > 0) {
            for(int i = 0; i < length - 1; i++) {
                currentNode = currentNode.next;
                sb.append(currentNode.value);
                sb.append(", ");
            }
            sb.append(currentNode.next.value);
        }
        sb.append("]");

        return sb.toString();
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
