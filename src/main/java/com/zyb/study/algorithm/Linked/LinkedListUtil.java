package com.zyb.study.algorithm.Linked;

import java.util.Objects;

public class LinkedListUtil {

    /**
     * 判断字符链表是否是回文字符链表
     * 实现思路：
     *  采用快慢指针的方式找到链表的中间结点，同时在遍历链表的过程中将链表的前半部分反转。
     *  找到链表的中间结点后需要判断链表的长度是否为奇数，如果为奇数，需要移动到链表的中间节点的下一个结点。
     *  在判断是否回文的过程中，将反转的链表恢复。
     * @param head
     * @return
     */
    public static boolean isPalindrome(Node<Character> head) {
        Objects.requireNonNull(head);

        if(head.next == null) {
            return true;
        }

        Node<Character> sp  = head; // Slow pointer
        Node<Character> fp = head.next; // Fast pointer

        Node<Character> reverseP = null; // Reverse pointer
        do {

            Node<Character> tp = sp; // Temporary pointer
            sp = sp.next;

            // 链表反转
            tp.next = reverseP;
            reverseP = tp;

            if(fp.next == null) { break; }
            fp = fp.next.next;

        } while (fp != null);


        Node<Character> recoveryP = sp; // Recovery pointer
        if(fp == null) { // 链表长度为奇数，sp指针在中点，需要指向下一个结点
            sp = sp.next;
        }

        boolean isPalindrome = true;
        while(sp != null) {
            isPalindrome &= sp.value.equals(reverseP.value);
            sp = sp.next;

            Node<Character> tp = reverseP; // Temporary pointer
            reverseP = reverseP.next;

            // 将反转的链表恢复
            tp.next = recoveryP;
            recoveryP = tp;
        }

        return isPalindrome;
    }

    /**
     * 链表反转
     * @param head
     * @param <E>
     * @return
     */
    public static <E> Node<E> reverse(Node<E> head) {
        Objects.requireNonNull(head);

        if(head.next == null) {
            return head;
        }

        Node<E> cp = head;
        Node<E> rp = null;
        while(cp != null) {
            Node<E> tp = cp;
            cp = cp.next;

            tp.next = rp;

            rp = tp;
        }
        return rp;
    }

    /**
     * 链表成环检测
     * 实现思路：
     *  采用快慢指针的方式，快指针的速度是慢指针的2倍。
     *  如果存在环，快慢指针会在环中相遇并返回true；
     *  如果不存在，会随着快指针的结束而返回false
     * @param head
     * @return
     */
    public static boolean checkLoop(Node<?> head) {
        Objects.requireNonNull(head);

        if(head.next == null) { return false; }

        Node<?> sp = head;
        Node<?> fp = head.next;

        while(fp != null && fp.next != null) {
            fp = fp.next.next;
            if(fp == sp) { return true; }

            sp = sp.next;
            if(sp == fp) { return true; }
        }

        return false;
    }

    /**
     * 合并两个有序链表
     * @param orderLinkedList1
     * @param orderLinkedList2
     * @param <E>
     * @return
     */
    public static <E extends Comparable<? super E>> Node<E> mergedOrderLinkedList(Node<E> orderLinkedList1, Node<E> orderLinkedList2) {
        Objects.requireNonNull(orderLinkedList1);
        Objects.requireNonNull(orderLinkedList2);

        Node<E> orderLinkedList1Cp = orderLinkedList1;
        Node<E> orderLinkedList2Cp = orderLinkedList2;

        Node<E> resultLinkedList = null;
        Node<E> resultLinkedListCp = null;
        if(orderLinkedList1Cp.value.compareTo(orderLinkedList2Cp.value) < 0) {
            resultLinkedList = resultLinkedListCp = orderLinkedList1Cp;
            orderLinkedList1Cp = orderLinkedList1Cp.next;
        } else {
            resultLinkedList = resultLinkedListCp = orderLinkedList2Cp;
            orderLinkedList2Cp = orderLinkedList2Cp.next;
        }

        while (orderLinkedList1Cp != null && orderLinkedList2Cp != null) {
            if(orderLinkedList1Cp.value.compareTo(orderLinkedList2Cp.value) < 0) {
                resultLinkedListCp.next = orderLinkedList1Cp;
                orderLinkedList1Cp = orderLinkedList1Cp.next;
            } else {
                resultLinkedListCp.next = orderLinkedList2Cp;
                orderLinkedList2Cp = orderLinkedList2Cp.next;
            }
            resultLinkedListCp = resultLinkedListCp.next;
        }

        resultLinkedListCp.next = orderLinkedList1Cp != null ? orderLinkedList1Cp : orderLinkedList2Cp;

        return resultLinkedList;
    }

    /**
     * 删除倒数第n个结点
     * 实现思路：
     *  采用快慢指针的方式，快指针比慢指针快reverseIndex，
     *  当快指针遍历完链表，慢指针指向链表的倒数第n个结点
     * @param head
     * @param reverseIndex
     * @param <E>
     * @return
     */
    public static <E> Node<E> reverseDelete(Node<E> head, int reverseIndex) {
        Objects.requireNonNull(head);
        if(reverseIndex < 1) {
            throw new IllegalArgumentException();
        }

        Node<E> fp = head;
        Node<E> sp = head;

        for(int i = 0; i < reverseIndex; i++) {
            if(fp == null) {
                throw new IllegalArgumentException();
            }
            fp = fp.next;
        }

        Node<E> psp = null;
        while(fp != null) {
            fp = fp.next;

            psp = sp;
            sp = sp.next;
        }

        if(psp == null) {
            psp = sp.next;
            return psp;
        }

        psp.next = psp.next.next;
        return head;
    }

    /**
     * 获取链表中点
     * 实现思路：
     *  采用快慢指针的方式，快指针的速度是慢指针的2倍，
     *  当快指针遍历完链表，慢指针指向链表的中点
     * @param head
     * @param <E>
     * @return
     */
    public static <E> Node<E> getIntermediateNode(Node<E> head) {
        Objects.requireNonNull(head);
        if(head.next == null) { return head; }

        Node<E> sp = head;
        Node<E> fp = head.next;

        while (fp != null && fp.next != null) {
            sp = sp.next;
            fp = fp.next.next;
        }
        return sp;
    }


    public static class Node<T> {
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
