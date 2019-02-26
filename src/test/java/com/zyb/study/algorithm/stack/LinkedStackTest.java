package com.zyb.study.algorithm.stack;

import org.junit.Assert;
import org.junit.Test;

public class LinkedStackTest {

    @Test
    public void test() {
        LinkedStack<String> stringLinkedStack = new LinkedStack<>();

        Assert.assertTrue(stringLinkedStack.isEmpty());

        stringLinkedStack.put("!");
        stringLinkedStack.put(" World");
        stringLinkedStack.put("Hello");

        Assert.assertFalse(stringLinkedStack.isEmpty());

        Assert.assertEquals(stringLinkedStack.size(), 3);

        StringBuilder sb = new StringBuilder();
        sb.append(stringLinkedStack.pop())
                .append(stringLinkedStack.pop())
                .append(stringLinkedStack.pop());

        Assert.assertEquals(sb.toString(), "Hello World!");

        Assert.assertTrue(stringLinkedStack.isEmpty());
    }
}
