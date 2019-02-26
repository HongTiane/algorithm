package com.zyb.study.algorithm.stack;

import com.zyb.study.algorithm.Linked.LinkedList;

import java.util.EmptyStackException;

public class LinkedStack<E> {
    private final LinkedList<E> stack = new LinkedList<>();

    public void put(E e) {
        stack.add(e);
    }

    public E pop() {
        if(isEmpty()) { throw new EmptyStackException(); }
        return stack.remove(stack.length() - 1);
    }

    public int size() {
        return stack.length();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
