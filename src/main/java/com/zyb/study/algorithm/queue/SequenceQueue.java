package com.zyb.study.algorithm.queue;

public class SequenceQueue<E> {
    private final E[] elements;
    private final int  capacity;

    private int head;
    private int tail;

    public SequenceQueue(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        }

        elements = (E[]) new Object[capacity];
        this.capacity = capacity;

        head = tail = 0;
    }

    public int size() {
        return tail - head;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public void enqueue(E e) {
        checkCapacity();
        elements[tail++] = e;
    }

    private void checkCapacity() {
        int size = size();
        if(size == capacity) {
            throw new RuntimeException("Queue is full.");
        }
        if(tail == capacity) {
            System.arraycopy(elements, head, elements, 0, size);
            tail -= head;
            head = 0;
        }
    }

    public E dequeue() {
        if(head == tail) {
            throw new RuntimeException("Queue is empty.");
        }
        E e = elements[head];
        elements[head++] = null;
        return e;
    }
}
