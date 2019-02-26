package com.zyb.study.algorithm.skiplist;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class SkipList<K extends Comparable<? super K>, V> {

    private static final int MAX_INDEX_LEVEL = 16;
    private static final Comparator comparator = Comparator.nullsFirst(Comparator.naturalOrder());

    private Node<K, V> head = new Node<>(null, null, null, null);

    private Node<K, V> maxIndexLevelHead = head;

    private int length = 0;
    private int maxIndexLevel = 1;

    public void insert(K key, V value) {
        int newNodeIndexLevel = randomGeneralIndexLevel();

        Node<K,V> preIndexNode = maxIndexLevelHead;
        Node<K,V> upIndexNode = null;
        if(newNodeIndexLevel > maxIndexLevel) {
            int count = newNodeIndexLevel - maxIndexLevel;
            upIndexNode = new Node<>(key, value, null, null);
            maxIndexLevelHead = new Node<>(null, null, upIndexNode, maxIndexLevelHead);
            count--;
            for(int i = 0; i < count; ++i) {
                Node<K, V> newNextIndexNode = new Node<>(key, value, null, maxIndexLevelHead.next);
                maxIndexLevelHead = new Node<>(null, null, newNextIndexNode, maxIndexLevelHead);
            }
        }

        int currentIndexLevel = maxIndexLevel;
        while(true) {
            if(preIndexNode.hasNext()) {
                int compare = comparator.compare(preIndexNode.next.key, key);
                if(compare == 0) {
                    if(Objects.nonNull(upIndexNode)) { // 需要判断！！！
                        upIndexNode.down = preIndexNode.next;
                    }
                    return;
                } else if(compare < 0) {
                    preIndexNode = preIndexNode.next;
                    continue;
                }
            }

            if(currentIndexLevel <= newNodeIndexLevel) { // 直接在当前索引层插入
                preIndexNode.next = new Node<>(key, value, preIndexNode.next, null);
                if(Objects.nonNull(upIndexNode)) {
                    upIndexNode.down = preIndexNode.next;
                }
                upIndexNode = preIndexNode.next;
            }
            if(preIndexNode.hasDown()) {
                preIndexNode = preIndexNode.down;
                currentIndexLevel--;
            } else {
                Node<K, V> temp = head;
                int count = 0;
                while (temp.hasNext()) {
                    count++;
                    temp= temp.next;
                }
                break;
            }
        }

        if(newNodeIndexLevel > maxIndexLevel) {
            maxIndexLevel = newNodeIndexLevel;
        }
        length++;
    }

    public V find(K key) {
        Node<K, V> firstPreIndexNode = findFirstPreIndexNode(key);
        if(Objects.nonNull(firstPreIndexNode)) {
            return firstPreIndexNode.next.value;
        }
        return null;
    }

    public V delete(K key) {
        Node<K, V> nextPreIndexNode = findFirstPreIndexNode(key);
        V deleteNodeValue = null;
        if(Objects.nonNull(nextPreIndexNode)) {
            deleteNodeValue = nextPreIndexNode.next.value;
            while(true) {
                nextPreIndexNode.next = nextPreIndexNode.next.next;
                if(nextPreIndexNode.hasDown()) {
                    nextPreIndexNode = nextPreIndexNode.down;
                    while(comparator.compare(nextPreIndexNode.next.key, key) != 0) {
                        nextPreIndexNode = nextPreIndexNode.next;
                    }
                } else {
                    break;
                }
            }
            while(!maxIndexLevelHead.hasNext()) {
                if(maxIndexLevelHead.hasDown()) {
                    maxIndexLevelHead = maxIndexLevelHead.down;
                    maxIndexLevel--;
                }
            }
            length--;
        }
        return deleteNodeValue;
    }

    public String print() {
        Map<K, Integer> keyMapIndex = new HashMap<>();
        Node<K, V> beginNode = head;
        int index = 0;
        while(beginNode.hasNext()) {
            keyMapIndex.put(beginNode.next.key, index++);
            beginNode = beginNode.next;
        }
        Node<K, V> currentHeadIndexNode = maxIndexLevelHead;
        Object[][] values = new Object[maxIndexLevel][length];
        for(int i = maxIndexLevel - 1; i >= 0; --i) {
            Node<K, V> preIndexNode = currentHeadIndexNode;
            while(preIndexNode.hasNext()) {
                values[i][keyMapIndex.get(preIndexNode.next.key)] = preIndexNode.next.value;
                preIndexNode = preIndexNode.next;
            }
            currentHeadIndexNode = currentHeadIndexNode.down;
        }

        StringBuilder buf = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        for(int i = maxIndexLevel - 1; i >= 0; --i) {
            for(int j = 0; j < length; ++j) {
                buf.append(Objects.isNull(values[i][j]) ? "" : values[i][j]);
                buf.append("\t\t");
            }
            buf.append(lineSeparator);
            buf.append(lineSeparator);
        }

        return buf.toString();
    }

    private Node<K,V> findFirstPreIndexNode(K key) {
        Node<K,V> preIndexNode = maxIndexLevelHead;
        while(true) {
            if(preIndexNode.hasNext()) {
                int compare = comparator.compare(preIndexNode.next.key, key);
                if(compare == 0) {
                    return preIndexNode;
                } else if(compare < 0) {
                    preIndexNode = preIndexNode.next;
                    continue;
                }
            }
            if(preIndexNode.hasDown()) {
                preIndexNode = preIndexNode.down;
            } else {
                return null;
            }
        }
    }

    /**
     * 随机生成索引层
     * @return 返回索引层数
     */
    private int randomGeneralIndexLevel() {
        int level = 1;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 1; i < MAX_INDEX_LEVEL; ++i) {
            if (random.nextInt() % 2 == 1) {
                level++;
            }
        }
        return level;
    }

    static class Node<K, V> {
        final K key;
        V value;
        Node<K,V> next, down;

        Node(K key, V value, Node<K, V> next, Node<K, V> down) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.down = down;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public boolean hasNext() {
            return Objects.nonNull(next);
        }

        public boolean hasDown() {
            return Objects.nonNull(down);
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Node) {
                Node<?,?> e = (Node<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }

}
