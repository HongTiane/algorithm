package com.zyb.study.algorithm.queue;

public class CircularSequenceQueue<E> {
    private final E[] elements;
    private final int  capacity;

    private int head;
    private int tail;

    public CircularSequenceQueue(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        }

        elements = (E[]) new Object[capacity];
        this.capacity = capacity;

        head = tail = 0;
    }

    public int size() {
        return (tail - head) & (capacity - 1);
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public void enqueue(E e) {
        int nextTail = (tail + 1) % capacity;
        if(nextTail == head) {
            throw new RuntimeException("Queue is full.");
        }
        elements[tail] = e;
        tail = nextTail;
    }

    public E dequeue() {
        if(head == tail) {
            throw new RuntimeException("Queue is empty.");
        }
        E e = elements[head];
        elements[head] = null;
        head = (head + 1) % capacity;
        return e;
    }
}
