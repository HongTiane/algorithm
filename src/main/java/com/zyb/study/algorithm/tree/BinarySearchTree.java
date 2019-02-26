package com.zyb.study.algorithm.tree;

import java.util.Comparator;
import java.util.Objects;

public class BinarySearchTree<E extends Comparable<? super E>> {

    private static final Comparator comparator = Comparator.nullsFirst(Comparator.naturalOrder());

    private Node<E> root;

    private int size = 0;

    public void insert(E e) {
        if(Objects.isNull(root)) {
            root = new Node<>(e, null, null);
        } else {
            Node<E> currentNode = root;
            while(true) {
                int compare = comparator.compare(e, currentNode.item);
                if(compare == 0) {
                    break;
                } else if(compare > 0) {
                    if(Objects.isNull(currentNode.right)) {
                        currentNode.right = new Node<>(e, null, null);
                        size++;
                        break;
                    } else {
                        currentNode = currentNode.right;
                        continue;
                    }
                } else {
                    if(Objects.isNull(currentNode.left)) {
                        currentNode.left = new Node<>(e, null, null);
                        size++;
                        break;
                    } else {
                        currentNode = currentNode.left;
                        continue;
                    }
                }
            }
        }
    }

    public E get(E e) {
        Node<E> currentNode = root;
        while(currentNode != null) {
            int compare = comparator.compare(e, currentNode.item);
            if(compare == 0) {
                return currentNode.item;
            } else if(compare > 0) {
                currentNode = currentNode.right;
            } else {
                currentNode = currentNode.left;
            }
        }
        return null;
    }

    public boolean delete(E e) {
        Node<E> currentNode = root;
        Node<E> deleteNodeFatherNode = null;
        while(currentNode != null) {
            int compare = comparator.compare(e, currentNode.item);
            if(compare == 0) {
                if(Objects.nonNull(currentNode.right)) { // 删除节点的右节点不为空，则获取右子树中最小的节点替换删除节点，同时在右子树删除最小节点
                    Node<E> rightMinNode = null;
                    if(Objects.isNull(currentNode.right.left)) {
                        rightMinNode = currentNode.right;
                    } else {
                        rightMinNode = currentNode.right.left;
                        Node<E> rightMinNodeFatherNode = currentNode.right;
                        while(Objects.nonNull(rightMinNode.left)) {
                            rightMinNodeFatherNode = rightMinNode;
                            rightMinNode = rightMinNode.left;
                        }
                        rightMinNodeFatherNode.left = rightMinNode.right; // 删除右子树最小节点，如果最小节点有右子树，使用右子树替换
                    }

                    if(Objects.isNull(deleteNodeFatherNode)) {
                        rightMinNode.left = currentNode.left;
                        rightMinNode.right = currentNode.right;
                        root = rightMinNode;
                    } else {
                        // 如果父节点不为空，直接替换删除节点的值为右子树最小节点的值
                        currentNode.item = rightMinNode.item;
                    }

                } else if(Objects.nonNull(currentNode.left)) {
                    if(Objects.isNull(deleteNodeFatherNode)) {
                        root = currentNode.left;
                    } else {
                        // 直接使用左子树的第一个节点替换当前节点
                        currentNode.item = currentNode.left.item;
                        currentNode.right = currentNode.left.right;
                        currentNode.left = currentNode.left.left;
                    }
                } else {
                    if(Objects.isNull(deleteNodeFatherNode)) {
                        root = null;
                    } else {
                        if(currentNode == deleteNodeFatherNode.left) {
                            deleteNodeFatherNode.left = null;
                        } else {
                            deleteNodeFatherNode.right = null;
                        }
                    }
                }
                size--;
                return true;
            } else if(compare > 0) {
                deleteNodeFatherNode = currentNode;
                currentNode = currentNode.right;
            } else {
                deleteNodeFatherNode = currentNode;
                currentNode = currentNode.left;
            }
        }

        return false;
    }

    static class Node<E> {
        E item;
        Node<E> left;
        Node<E> right;

        public Node(E item, Node<E> left, Node<E> right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }

    }
}
