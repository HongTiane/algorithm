package com.zyb.study.algorithm.linked;

import com.zyb.study.algorithm.Linked.LinkedList;
import org.junit.Assert;
import org.junit.Test;

public class LinkedListTest {

    @Test
    public void testAdd() {
        LinkedList<String> stringLinkedList = new LinkedList<>();

        Assert.assertEquals(stringLinkedList.add("Hello"), 0);
        Assert.assertEquals(stringLinkedList.add("World"), 1);

        Assert.assertEquals(stringLinkedList.toString(), "[Hello, World]");
    }

    @Test
    public void testGet() {

        LinkedList<String> stringLinkedList = new LinkedList<>();

        stringLinkedList.add("Hello");
        stringLinkedList.add("World");

        Assert.assertEquals(stringLinkedList.get(0), "Hello");
        Assert.assertEquals(stringLinkedList.get(1), "World");
    }

    @Test
    public void testInsert() {

        // 测试插入空链表
        LinkedList<String> stringLinkedList = new LinkedList<>();

        stringLinkedList.insert(0, "Hello");

        Assert.assertEquals(stringLinkedList.length(), 1);
        Assert.assertEquals(stringLinkedList.get(0), "Hello");

        // 插入到最后一个元素的后一个位置
        stringLinkedList.insert(stringLinkedList.length(), "World");
        Assert.assertEquals(stringLinkedList.length(), 2);
        Assert.assertEquals(stringLinkedList.get(1), "World");
    }

    @Test
    public void testDelete() {
        LinkedList<String> stringLinkedList = new LinkedList<>();
        stringLinkedList.add("Hello");
        stringLinkedList.add("World");

        Assert.assertEquals(stringLinkedList.remove(0), "Hello");
        Assert.assertEquals(stringLinkedList.length(), 1);
        Assert.assertEquals(stringLinkedList.remove("World"), "World");
        Assert.assertEquals(stringLinkedList.length(), 0);
    }
}
