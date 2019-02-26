package com.zyb.study.algorithm.array;

import java.util.Objects;

public class Array<T> {

    private final static int DEFAULT_INIT_CAPACITY = 16;

    private T[] a;

    private int capacity = 0;
    private int size = 0;

    public Array() {
        a = (T[]) new Object[DEFAULT_INIT_CAPACITY];
        capacity = DEFAULT_INIT_CAPACITY;
    }

    public Array(final int capacity) {

        if(capacity < 0) {
            throw new IllegalArgumentException();
        }

        a = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public Array(final Array<T> init) {
        Objects.requireNonNull(a);

        size = init.size();

        capacity = size + size / 2;
        a = (T[]) new Object[capacity];

        for(int i = 0; i < size; i++) {
            a[i] = init.get(i);
        }

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T get(final int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return a[index];
    }

    public int add(final T e) {
        checkCapacity();
        a[size] = e;
        return size++;
    }
    
    public void insert(final int index, final T e) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if(size > 0) {
            shiftRight(index, 1);
        }
        a[index] = e;
    }

    private void checkCapacity() {
        if(size >= capacity) {
            // 扩容策略
            int newCapacity = capacity + capacity / 2;

            T[] newA = (T[]) new Object[newCapacity];
            for(int i = 0; i < capacity; i++) {
                newA[i] = a[i];
                a[i] = null;
            }
            a = newA;
            capacity = newCapacity;
        }
    }

    private void shiftRight(int beginIndex, int count) {
        if(!shiftRightCheckCapacity(beginIndex, count)) {
            for(int i = size - 1; i >= beginIndex; i--) {
                a[i] = a[i + count];
                a[i] = null;
            }
        }

        size += count;
    }

    private boolean shiftRightCheckCapacity(int beginIndex, int count) {
        int aftershiftRightSize = size + count;
        if(aftershiftRightSize > capacity) {
            // 扩容策略
            int newCapacity = aftershiftRightSize + aftershiftRightSize / 2;

            T[] newA = (T[]) new Object[newCapacity];
            for(int i = 0; i < beginIndex; i++) {
                newA[i] = a[i];
                a[i] = null;
            }
            for(int i = beginIndex; i < size; i++) {
                newA[beginIndex + count] = a[i];
                a[i] = null;
            }
            a = newA;
            capacity = newCapacity;

            return true;
        }

        return false;
    }

    public T remove(final int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        T e = a[index];
        a[index] = null;
        if(index < size - 1) {
            shiftLeft(index, 1);
        }
        return e;
    }

    public T removeLast() {
        if(size < 1) {
            throw new IndexOutOfBoundsException();
        }

        T e = a[--size];
        a[size] = null;
        return e;
    }

    private void shiftLeft(int beginIndex, int count) {
        for(int i = beginIndex; i < size; i++) {
            a[i - count] = a[i];
            a[i] = null;
        }

        size -= count;
    }

    @Override
    public String toString() {
        StringBuffer sb  = new StringBuffer();
        sb.append("[");
        if(size > 0) {
            if(size > 1) {
                for(int i = 0; i < size - 1; i++) {
                    sb.append(a[i] + ", ");
                }
            }
            sb.append(a[size - 1]);
        }
        sb.append("]");
        return sb.toString();
    }
}
