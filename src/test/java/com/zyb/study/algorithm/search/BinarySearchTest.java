package com.zyb.study.algorithm.search;

import org.junit.Assert;
import org.junit.Test;

public class BinarySearchTest {

    private final int[] elements = {1, 2, 3, 3, 3, 4, 5, 7, 7, 7, 8, 9, 9, 10, 10, 10, 11, 12, 13};

    @Test
    public void testSearchFirstEqual() {

        Assert.assertEquals(BinarySearch.searchFirstEqual(elements, 1), 0);
        Assert.assertEquals(BinarySearch.searchFirstEqual(elements, 3), 2);
        Assert.assertEquals(BinarySearch.searchFirstEqual(elements, 7), 7);
        Assert.assertEquals(BinarySearch.searchFirstEqual(elements, 13), elements.length - 1);
        Assert.assertEquals(BinarySearch.searchFirstEqual(elements, 0), -1);
        Assert.assertEquals(BinarySearch.searchFirstEqual(elements, 6), -1);
    }

    @Test
    public void testSearchLastEqual() {
        Assert.assertEquals(BinarySearch.searchLastEqual(elements, 1), 0);
        Assert.assertEquals(BinarySearch.searchLastEqual(elements, 3), 4);
        Assert.assertEquals(BinarySearch.searchLastEqual(elements, 7), 9);
        Assert.assertEquals(BinarySearch.searchLastEqual(elements, 13), elements.length - 1);
        Assert.assertEquals(BinarySearch.searchLastEqual(elements, 0), -1);
        Assert.assertEquals(BinarySearch.searchLastEqual(elements, 6), -1);
    }

    @Test
    public void testSearchFirstGe() {
        Assert.assertEquals(BinarySearch.searchFirstGe(elements, 1), 0);
        Assert.assertEquals(BinarySearch.searchFirstGe(elements, 3), 2);
        Assert.assertEquals(BinarySearch.searchFirstGe(elements, 7), 7);
        Assert.assertEquals(BinarySearch.searchFirstGe(elements, 13), elements.length - 1);
        Assert.assertEquals(BinarySearch.searchFirstGe(elements, 0), 0);
        Assert.assertEquals(BinarySearch.searchFirstGe(elements, 6), 7);
        Assert.assertEquals(BinarySearch.searchFirstGe(elements, 14), -1);
    }

    @Test
    public void testSearchLastLe() {
        Assert.assertEquals(BinarySearch.searchLastLe(elements, 1), 0);
        Assert.assertEquals(BinarySearch.searchLastLe(elements, 3), 4);
        Assert.assertEquals(BinarySearch.searchLastLe(elements, 7), 9);
        Assert.assertEquals(BinarySearch.searchLastLe(elements, 13), elements.length - 1);
        Assert.assertEquals(BinarySearch.searchLastLe(elements, 0), -1);
        Assert.assertEquals(BinarySearch.searchLastLe(elements, 6), 6);
        Assert.assertEquals(BinarySearch.searchLastLe(elements, 14), elements.length - 1);
    }

    @Test
    public void test() {
        int[] circulElements = { 4, 5, 6, 1, 2, 3 };

        Assert.assertEquals(BinarySearch.searchInCircularArray(circulElements, 1), 3);

        Assert.assertEquals(BinarySearch.searchInCircularArray(circulElements, 4), 0);

        Assert.assertEquals(BinarySearch.searchInCircularArray(circulElements, 6), 2);

        Assert.assertEquals(BinarySearch.searchInCircularArray(circulElements, 3), 5);

    }
}
