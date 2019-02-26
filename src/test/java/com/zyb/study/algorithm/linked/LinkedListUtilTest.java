package com.zyb.study.algorithm.linked;

import com.zyb.study.algorithm.Linked.LinkedListUtil;
import com.zyb.study.algorithm.Linked.LinkedListUtil.Node;
import org.junit.Assert;
import org.junit.Test;

public class LinkedListUtilTest {

    @Test
    public void testIsPalindromeWithOne() {
        // 测试链表只有一个元素
        Node<Character> head = new Node<Character>('c');
        Assert.assertTrue(LinkedListUtil.isPalindrome(head));

    }

    @Test
    public void testIsPalindromeWithTwo() {

        // 测试两个元素的回文链表
        Node<Character> head = new Node<Character>('c');
        Node<Character> cp = head;
        cp.next = new Node<>('c');
        cp = cp.next;
        Assert.assertTrue(LinkedListUtil.isPalindrome(head));

    }

    @Test
    public void testIsPalindromeWithThree() {

        // 测试两个元素的回文链表
        Node<Character> head = new Node<Character>('c');
        Node<Character> cp = head;
        cp.next = new Node<>('o');
        cp = cp.next;
        cp.next = new Node<>('c');
        cp = cp.next;
        Assert.assertTrue(LinkedListUtil.isPalindrome(head));

        // 判断链表是否改变
        Assert.assertEquals(parseString(head),"coc");

    }

    @Test
    public void testReverseWithOne() {
        Node<Integer> head = new Node<>(1);
        Assert.assertEquals(parseString(LinkedListUtil.reverse(head)), "1");
    }

    @Test
    public void testReverseWithTwo() {
        Node<Integer> head = new Node<>(1);
        Node<Integer> cp = head;
        cp.next = new Node<>(2);
        Assert.assertEquals(parseString(LinkedListUtil.reverse(head)), "21");
    }

    @Test
    public void testReverseWithThree() {
        Node<Integer> head = new Node<>(1);
        Node<Integer> cp = head;
        cp.next = new Node<>(2);
        cp = cp.next;
        cp.next= new Node<>(3);
        Assert.assertEquals(parseString(LinkedListUtil.reverse(head)), "321");
    }

    @Test
    public void testCheckLoop() {
        // 检测链表长度为1的环
        Node<Integer> head = createLoopLinkedList(1, 0);
        Assert.assertFalse(LinkedListUtil.checkLoop(head));

        /**
         * 检测链表长度为2的环
         */
        // 环的入口点为0
        head = createLoopLinkedList(2, 0);
        Assert.assertTrue(LinkedListUtil.checkLoop(head));
        // 环的入口点为1
        head = createLoopLinkedList(2, 1);
        Assert.assertTrue(LinkedListUtil.checkLoop(head));

        for(int i = 0; i < 100; i++) {
            head = createLoopLinkedList(100, i);
            Assert.assertTrue(LinkedListUtil.checkLoop(head));
        }

    }

    @Test
    public void testCheckLoop2() {
        // 检测长度为 length 的链表的环

        int length = 10000;
        for(int i = 0; i < length; i++) {
            Node<Integer> head = createLoopLinkedList(length, i);
            Assert.assertTrue(LinkedListUtil.checkLoop(head));
        }

    }

    @Test
    public void testCheckLoopWithNotLoop() {
        // 检测无环链表
        Node<Integer> head = createLinkedList(1);
        Assert.assertFalse(LinkedListUtil.checkLoop(head));

        head = createLinkedList(2);
        Assert.assertFalse(LinkedListUtil.checkLoop(head));

        head = createLinkedList(10);
        Assert.assertFalse(LinkedListUtil.checkLoop(head));

    }

    @Test
    public void testMergedOrderLinkedList() {
        Node<String> cp = null;
        Node<String> head1 = new Node<>("hj");
        cp = head1;
        cp.next = new Node<>("nn");
        cp = cp.next;
        cp.next = new Node<>("so");
        cp = cp.next;
        cp.next = new Node<>("yyy");

        Node<String> head2 = new Node<>("mx");
        cp = head2;
        cp.next = new Node<>("oorq");
        cp = cp.next;
        cp.next = new Node<>("ttdas");

        Assert.assertEquals(parseString(LinkedListUtil.mergedOrderLinkedList(head1, head2)), "hjmxnnoorqsottdasyyy");

    }

    @Test
    public void testReverseDelete() {
        // 链表长度为1
        Assert.assertEquals(parseString(LinkedListUtil.reverseDelete(createLinkedList(1), 1)), "");

        // 链表长度为2
        Assert.assertEquals(parseString(LinkedListUtil.reverseDelete(createLinkedList(2), 1)), "1");
        // 链表长度为2
        Assert.assertEquals(parseString(LinkedListUtil.reverseDelete(createLinkedList(2), 2)), "2");

        // 链表长度为3
        Assert.assertEquals(parseString(LinkedListUtil.reverseDelete(createLinkedList(3), 1)), "12");
        // 链表长度为3
        Assert.assertEquals(parseString(LinkedListUtil.reverseDelete(createLinkedList(3), 2)), "13");
        // 链表长度为3
        Assert.assertEquals(parseString(LinkedListUtil.reverseDelete(createLinkedList(3), 3)), "23");
    }

    @Test
    public void testGetIntermediateNode() {
        Assert.assertEquals(LinkedListUtil.getIntermediateNode(createLinkedList(1)).value, Integer.valueOf(1));

        Assert.assertEquals(LinkedListUtil.getIntermediateNode(createLinkedList(2)).value, Integer.valueOf(1));

        Assert.assertEquals(LinkedListUtil.getIntermediateNode(createLinkedList(3)).value, Integer.valueOf(2));

        Assert.assertEquals(LinkedListUtil.getIntermediateNode(createLinkedList(4)).value, Integer.valueOf(2));

        Assert.assertEquals(LinkedListUtil.getIntermediateNode(createLinkedList(5)).value, Integer.valueOf(3));
    }

    private Node<Integer> createLinkedList(int length) {
        if(length <= 0) {
            throw new IllegalArgumentException();
        }
        Node<Integer> head = new Node<>(1);
        Node<Integer> cp = head;
        for(int i = 2; i <= length; i++) {
            cp.next = new Node<>(i);
            cp = cp.next;
        }
        return head;
    }

    private <E> String parseString(Node<E> head) {
        if(head == null) {
            return "";
        }
        Node<E> cp = head;
        StringBuilder sb = new StringBuilder();
        while (cp != null) {
            sb.append(cp.value);
            cp = cp.next;
        }
        return sb.toString();
    }

    private Node<Integer> createLoopLinkedList(int length, int loopEntry) {
        if(length <= 0 || loopEntry < 0 || loopEntry >= length) {
            throw new IllegalArgumentException();
        }

        Node<Integer> head = new Node<>(1);

        Node<Integer> loopEntryNode = null;
        if(loopEntry == 0) {
            loopEntryNode = head;
        }

        Node<Integer> cp = head;

        for(int i = 1; i < length; i++) {
            cp.next = new Node<>(i);
            if(loopEntry == i) { loopEntryNode = cp.next; }
            cp = cp.next;
        }

        cp.next = loopEntryNode;
        return head;
    }
}
