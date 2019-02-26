package com.zyb.study.algorithm.lru;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 基于双向链表和哈希表的LRU缓存实现
 * @param <E>
 */
public class LRUCache<E> {

    private final Node<E> head;

    private final Map<E, Node<E>> valueNodeMap;

    private final int cacheCapacity;

    private int cacheSize = 0;

    public LRUCache(int cacheCapacity) {
        if(cacheCapacity <= 0 ) {
            throw new IllegalArgumentException("cache capacity: " + cacheCapacity);
        }

        head = new Node<>(null, null, null);
        head.next = head;
        head.prev = head;

        valueNodeMap = new HashMap<>(cacheCapacity);
        this.cacheCapacity = cacheCapacity;
    }

    public void add(E e) {
        Node<E> node = null;
        if(valueNodeMap.containsKey(e)) {
            node = valueNodeMap.get(e);
            node.prev.next = node.next;
            node.next.prev = node.prev;

        } else {
            node = new Node<>(e, null, null);
            if(cacheSize == cacheCapacity) { // 删除尾部节点
                head.prev.prev.next = head;
                head.prev = head.prev.prev;
            } else {
                cacheSize++;
            }
        }
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    public E get(E e) {
        if(valueNodeMap.containsKey(e)) {
            Node<E> node = valueNodeMap.get(e);

            node.prev.next = node.next;
            node.next.prev = node.prev;

            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            node.prev = head;

            return node.value;
        }
        return null;
    }

    public E remove(E e) {
        if(valueNodeMap.containsKey(e)) {
            Node<E> node = valueNodeMap.get(e);

            node.prev.next = node.next;
            node.next.prev = node.prev;

            return node.value;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> currentP = head.next;
        if(Objects.nonNull(currentP)) {
            sb.append(currentP.value);
            currentP = currentP.next;
            while (Objects.nonNull(currentP)) {
                sb.append(", ");
                sb.append(currentP.value);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    static class Node<E> {
        E value;
        Node<E> next, prev;

        public Node(E value, Node<E> next, Node<E> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public E getValue() { return value; }

        public final E setValue(E value) {
            E old = this.value;
            this.value = value;
            return old;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
