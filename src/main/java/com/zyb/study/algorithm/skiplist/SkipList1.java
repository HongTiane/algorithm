package com.zyb.study.algorithm.skiplist;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 跳跃表
 * @param <E>
 */
public class SkipList1<E extends Comparable<? super E>> {

    private static final int MAX_INDEX_LEVEL = 16;
    private static final Comparator comparator = Comparator.nullsFirst(Comparator.naturalOrder());

    private final Node<E> head = new Node<>(MAX_INDEX_LEVEL);

    private int length = 0;
    private int maxIndexLevel = 0;

    public SkipList1() {}

    /**
     * 插入一个元素
     * @param e 带插入的元素
     */
    public void insert(E e) {

        int newNodeMaxIndexLevel = randomGeneralMaxIndexLevel();
        Node<E> newNode = new Node<>(e, newNodeMaxIndexLevel);

        Node<E> preNode = head;
        IndexNode<E> preIndexNode = null;
        int currentIndexLevel = maxIndexLevel > newNodeMaxIndexLevel ? maxIndexLevel -1 : newNodeMaxIndexLevel - 1;
        preIndexNode = head.getIndexNode(currentIndexLevel);
        while(!preIndexNode.hasNext()) { // 获取{@code preIndexNode.hasNext()}为{@code true}的索引层（{@code newNodeMaxIndexLevel > maxIndexLevel}时{@code !preIndexNode.hasNext()}为{@code true}）
            if(currentIndexLevel < newNode.getMaxIndexLevel()) {
                preIndexNode.setNext(newNode.getIndexNode(currentIndexLevel));
            }
            if(--currentIndexLevel < 0) {
                break;
            }
            preIndexNode = head.getIndexNode(currentIndexLevel);
        }

        if(currentIndexLevel < 0) { // 表示索引层已经完成遍历(插入到第一个元素时{@code currentIndexLevel < 0})
            while(preNode.hasNext()) {
                if(comparator.compare(preNode.getNext().getValue(), e) < 0) {
                    preNode = preNode.getNext();
                } else {
                    break;
                }
            }
            newNode.setNext(preNode.setNext(newNode));

            length++;
            if(newNodeMaxIndexLevel > maxIndexLevel) {
                maxIndexLevel = newNodeMaxIndexLevel;
            }
            return;
        }
        int compare;
        while(true) {
            compare = comparator.compare(preIndexNode.getNext().getValue(), e);
            if(compare >= 0) { // 查找到第一个大于等于待插入的元素的节点，所以在该节点之前插入目标元素
                if(currentIndexLevel < newNode.getMaxIndexLevel()) {
                    IndexNode<E> newIndexNode = newNode.getIndexNode(currentIndexLevel);
                    newIndexNode.setNext(preIndexNode.setNext(newIndexNode));
                }

                if(currentIndexLevel == 0) {
                    preNode = preIndexNode.getNode();
                    while(comparator.compare(preNode.getNext().getValue(), e) < 0) { // 一定能找到第一个大于等于待插入的元素的节点
                        preNode = preNode.getNext();
                    }
                    newNode.setNext(preNode.setNext(newNode));

                    length++;
                    if(newNodeMaxIndexLevel > maxIndexLevel) {
                        maxIndexLevel = newNodeMaxIndexLevel;
                    }

                    return;

                } else { // 降级查找
                    preIndexNode = preIndexNode.getNode().getIndexNode(--currentIndexLevel);
                }

            } else {
                preIndexNode = preIndexNode.getNext();
            }

            while(!preIndexNode.hasNext()) { // 获取{@code preIndexNode.hasNext()}为{@code true}的索引层
                if(currentIndexLevel < newNode.getMaxIndexLevel()) {
                    IndexNode<E> newIndexNode = newNode.getIndexNode(currentIndexLevel);
                    newIndexNode.setNext(preIndexNode.setNext(newIndexNode));
                }
                if(--currentIndexLevel < 0) {
                    break;
                }
                preIndexNode = preIndexNode.getNode().getIndexNode(currentIndexLevel);
            }
            if(currentIndexLevel < 0) { // 表示索引层已经完成遍历
                preNode = preIndexNode.getNode();
                while(preNode.hasNext()) {
                    if(comparator.compare(preNode.getNext().getValue(), e) < 0) {
                        preNode = preNode.getNext();
                    } else {
                        break;
                    }
                }
                newNode.setNext(preNode.setNext(newNode));

                length++;
                if(newNodeMaxIndexLevel > maxIndexLevel) {
                    maxIndexLevel = newNodeMaxIndexLevel;
                }

                return;
            }
        }
    }

    /**
     * 查找元素
     * @param e 待查找的元素
     * @return 返回目标元素，如果未找到返回{@code null}
     */
    public E find(E e) {
        AbstractNode<E, ?> firstPreAbstractNode = findFirstPreAbstractNode(e);
        if(firstPreAbstractNode != null) {
            return firstPreAbstractNode.getNext().getValue();
        }
        return null;
    }

    /**
     * 删除元素
     * @param e 待删除的元素
     * @return 返回删除元素，如果元素不存在返回{@code null}
     */
    public E delete(E e) {
        AbstractNode<E, ?> firstPreAbstractNode = findFirstPreAbstractNode(e);
        if(firstPreAbstractNode == null) {
            return null;
        }
        Node<E> preNode = null;
        if(firstPreAbstractNode instanceof Node) {
            preNode = (Node<E>) firstPreAbstractNode;
        } else {
            IndexNode<E> nextPreIndexNode = (IndexNode<E>) firstPreAbstractNode;
            int currentIndexLevel = nextPreIndexNode.getIndexLevel();
            while(true) {
                nextPreIndexNode.setNext(nextPreIndexNode.getNext().getNext());
                if(--currentIndexLevel < 0) {
                    break;
                }
                nextPreIndexNode = nextPreIndexNode.getNode().getIndexNode(currentIndexLevel);
                while(comparator.compare(nextPreIndexNode.getNext().getValue(), e) != 0) {
                    nextPreIndexNode = nextPreIndexNode.getNext();
                }
            }
            preNode = nextPreIndexNode.getNode();
            while(comparator.compare(preNode.getNext().getValue(), e) != 0) {
                preNode = preNode.getNext();
            }
        }
        Node<E> deleteNode = preNode.getNext();
        preNode.setNext(deleteNode.getNext());
        length--;
        if(maxIndexLevel == deleteNode.getMaxIndexLevel()) {
            while(!head.getIndexNode(--maxIndexLevel).hasNext()) {
                if(maxIndexLevel == 0) {
                    break;
                }
            }
        }
        return deleteNode.getValue();
    }

    /**
     * 打印跳跃表的结构
     * @return 返回跳跃表的结构字符串形式
     */
    public String print() {
        String[][] valutToStrings = new String[MAX_INDEX_LEVEL + 1][length];
        Node<E> preNode = head;
        int index = 0;
        while(preNode.hasNext()) {
            preNode = preNode.getNext();
            E value = preNode.getValue();
            String valueStr = Objects.nonNull(value) ? Objects.toString(value) : "";
            for(int i = 1, n = preNode.getMaxIndexLevel(); i <= n; ++i) {
                valutToStrings[i][index] = valueStr;
            }
            valutToStrings[0][index] = valueStr;
            index++;
        }

        StringBuilder buf = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        String valutToString = null;
        for(int i = maxIndexLevel; i > 0; --i) {

            int endIndex = length - 1;
            while(Objects.isNull(valutToStrings[i][endIndex])) {
                if(--endIndex < 0) {
                    break;
                }
            }

            for(int j = 0; j < endIndex; ++j) {
                valutToString = valutToStrings[i][j];
                buf.append(Objects.isNull(valutToString) ? "" : valutToString);
                buf.append("\t\t");
            }
            valutToString = valutToStrings[i][endIndex];
            buf.append(Objects.isNull(valutToString) ? "" : valutToString);
            buf.append(lineSeparator);
            buf.append(lineSeparator);
        }

        for(int j = 0; j < length - 1; ++j) {
            valutToString = valutToStrings[0][j];
            buf.append(Objects.isNull(valutToString) ? "" : valutToString);
            buf.append("\t\t");
        }
        valutToString = valutToStrings[0][length - 1];
        buf.append(Objects.isNull(valutToString) ? "" : valutToString);

        return buf.toString();
    }

    private AbstractNode<E, ? > findFirstPreAbstractNode(E e) {
        Node<E> preNode = head; // {@code head}是哨兵
        int currentIndexLevel = maxIndexLevel - 1;
        IndexNode<E> preIndexNode = head.getIndexNode(currentIndexLevel);

        if(currentIndexLevel < 0) { // 表示索引层已经完成遍历
            while(preNode.hasNext()) {
                if(comparator.compare(preNode.getNext().getValue(), e) == 0) {
                    return preNode;
                } else {
                    preNode = preNode.getNext();
                }
            }
            return null;
        }
        int compare;
        while(true) {
            compare = comparator.compare(preIndexNode.getNext().getValue(), e);
            if(compare == 0) {
                return preIndexNode;

            } else if(compare < 0){
                preIndexNode = preIndexNode.getNext();
            } else {
                if(--currentIndexLevel > 0) {
                    preIndexNode = preIndexNode.getNode().getIndexNode(currentIndexLevel);
                }
            }
            while(!preIndexNode.hasNext()) { // 获取{@code preIndexNode.hasNext()}为{@code true}的索引层
                if(--currentIndexLevel < 0) {
                    break;
                }
                preIndexNode = preIndexNode.getNode().getIndexNode(currentIndexLevel);
            }
            if(currentIndexLevel < 0) { // 表示索引层已经完成遍历
                preNode = preIndexNode.getNode();
                while(preNode.hasNext()) {
                    if(comparator.compare(preNode.getNext().getValue(), e) == 0) {
                        return preNode;
                    } else {
                        preNode = preNode.getNext();
                    }
                }
                return null;
            }

        }
    }

    /**
     * 随机生成索引级数
     * @return 返回索引级数
     */
    private int randomGeneralMaxIndexLevel() {
        int level = 0;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 1; i < MAX_INDEX_LEVEL; ++i) {
            if (random.nextInt() % 2 == 1) {
                level++;
            }
        }
        return level;
    }

    private class IndexNode<E> extends AbstractNode<E, IndexNode<E>> {

        private Node<E> node;
        private int indexLevel;

        public IndexNode() {
        }

        public IndexNode(Node<E> node, int indexLevel) {
            this(null, null, node, indexLevel);
        }

        public IndexNode(E value, Node<E> node, int indexLevel) {
            this(value, null, node, indexLevel);
        }

        public IndexNode(E value, IndexNode<E> next, Node<E> node, int indexLevel) {
            super(value, next);
            this.node = node;
            this.indexLevel = indexLevel;
        }

        public Node<E> getNode() {
            return node;
        }

        public void setNode(Node<E> node) {
            this.node = node;
        }

        public int getIndexLevel() {
            return indexLevel;
        }

        public void setIndexLevel(int indexLevel) {
            this.indexLevel = indexLevel;
        }
    }

    private class Node<E> extends AbstractNode<E, Node<E>> {
        private final IndexNode<E>[] forwards = new IndexNode[MAX_INDEX_LEVEL];
        private int maxIndexLevel;

        public Node() {}

        public Node(int maxIndexLevel) {
            this(null, null, maxIndexLevel);
        }

        public Node(E value, int maxIndexLevel) {
            this(value, null, maxIndexLevel);
        }

        public Node(E value, Node<E> next, int maxIndexLevel) {
            super(value, next);
            checkMaxIndexLevel(maxIndexLevel);
            for(int i = 0; i < maxIndexLevel; ++i) {
                forwards[i] = new IndexNode<E>(value, this, i);
            }
            this.maxIndexLevel = maxIndexLevel;
        }

        public void updateMaxIndexLevel(int newMaxIndexLevel) {
            checkMaxIndexLevel(maxIndexLevel);
            if(maxIndexLevel > newMaxIndexLevel) {
                for(int i = newMaxIndexLevel; i < maxIndexLevel; ++i) {
                    forwards[i] = null;
                }
            } else if(maxIndexLevel < newMaxIndexLevel) {
                for(int i = maxIndexLevel; i < newMaxIndexLevel; ++i) {
                    forwards[i] = new IndexNode<E>(value, this, i);
                }
            }
            this.maxIndexLevel = newMaxIndexLevel;
        }

        public IndexNode<E>[] getForwards() {
            return forwards;
        }

        public IndexNode<E> getIndexNode(int indexLevel) {
            checkIndexLevel(indexLevel);
            return forwards[indexLevel];
        }

        public IndexNode<E> setIndexNode(IndexNode<E> newIndexNode, int indexLevel) {
            checkIndexLevel(indexLevel);
            IndexNode<E> oldIndexNode = forwards[indexLevel];
            forwards[indexLevel] = newIndexNode;
            return oldIndexNode;
        }

        public IndexNode<E> getMaxIndexNode() {
            return getIndexNode(maxIndexLevel - 1);
        }

        public IndexNode<E> getFirstIndexNode() {
            return getIndexNode(0);
        }

        private void checkIndexLevel(int indexLevel) {
            if(indexLevel < 0 || indexLevel >= maxIndexLevel) {
                throw new IllegalArgumentException("indexLevel: " +  indexLevel);
            }
        }

        private void checkMaxIndexLevel(int maxIndexLevel) {
            if(maxIndexLevel < 0 || maxIndexLevel > MAX_INDEX_LEVEL) {
                throw new IllegalArgumentException("maxIndexLevel: " +  maxIndexLevel);
            }
        }

        public int getMaxIndexLevel() {
            return maxIndexLevel;
        }
    }


    private class AbstractNode<E, N extends AbstractNode<E, N>> {
        protected E value;
        protected N next;

        public AbstractNode() {
        }

        public AbstractNode(E value) {
            this.value = value;
        }

        public AbstractNode(E value, N next) {
            this.value = value;
            this.next = next;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public N getNext() {
            return next;
        }

        public N setNext(N next) {
            N old = this.next;
            this.next = next;
            return old;
        }

        public boolean hasNext() {
            return next != null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AbstractNode<?, ?> that = (AbstractNode<?, ?>) o;
            return Objects.equals(value, that.value) &&
                    Objects.equals(next, that.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, next);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

}
