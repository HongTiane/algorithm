package com.zyb.study.algorithm.queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class ConcurrentQueue<E> {
    private final AtomicReferenceArray<E> elements;
    private final AtomicInteger head;
    private final AtomicInteger tail;

    public ConcurrentQueue(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        }

        elements = new AtomicReferenceArray<>(capacity);
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
    }

    public void enqueue(E e) {
        while(true) {
            int headValue = head.get();
            int tailValue = tail.get();

            int tailNextValue = (tailValue + 1) % elements.length();
            if(tailNextValue == headValue) {
                throw new RuntimeException("Queue is full.");
            }

            E tailElements = elements.get(tailValue);
            if(tailElements != null) {
                tail.compareAndSet(tailValue, tailNextValue);
            } else {
                if(elements.compareAndSet(tailValue, tailElements, e)) {
                    tail.compareAndSet(tailValue, tailNextValue);
                    return;
                }
            }
        }
    }

    public E dequeue() {
        while(true) {
            int headValue = head.get();
            int tailValue = tail.get();

            if(headValue == tailValue) {
                throw new RuntimeException("Queue is empty.");
            }

            int headNextValue = (headValue + 1) % elements.length();

            E headElements = elements.get(headValue);
            if(headElements == null) {
                head.compareAndSet(headValue, headNextValue);
            } else {
                if(elements.compareAndSet(headValue, headElements, null)) {
                    head.compareAndSet(headValue, headNextValue);
                    return headElements;
                }
            }
        }
    }
}
