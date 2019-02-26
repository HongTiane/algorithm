package com.zyb.study.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class SortUtilTest {

    @Test
    public void testBubbleSort() {
        int[] randomNumberArray = generalRandomNumberArray(2);
        System.out.println("Random Number Array: ");
        System.out.println(Arrays.toString(randomNumberArray));

        SortUtil.bubbleSort(randomNumberArray);
        System.out.println("Ordered Array: ");
        System.out.println(Arrays.toString(randomNumberArray));
    }

    @Test
    public void testInsertionSort() {
        int[] randomNumberArray = generalRandomNumberArray(20);
        System.out.println("Random Number Array: ");
        System.out.println(Arrays.toString(randomNumberArray));

        SortUtil.insertionSort(randomNumberArray);
        System.out.println("Ordered Array: ");
        System.out.println(Arrays.toString(randomNumberArray));
    }

    @Test
    public void testSelectionSort() {
        int[] randomNumberArray = generalRandomNumberArray(10);
        System.out.println("Random Number Array: ");
        System.out.println(Arrays.toString(randomNumberArray));

        SortUtil.selectionSort(randomNumberArray);
        System.out.println("Ordered Array: ");
        System.out.println(Arrays.toString(randomNumberArray));
    }

    @Test
    public void testMergeSort() {
        int[] randomNumberArray = generalRandomNumberArray(10);
        System.out.println("Random Number Array: ");
        System.out.println(Arrays.toString(randomNumberArray));

        SortUtil.mergeSort(randomNumberArray);
        System.out.println("Ordered Array: ");
        System.out.println(Arrays.toString(randomNumberArray));
    }

    @Test
    public void testQuickSort() {
        int[] randomNumberArray = generalRandomNumberArray(10);
        System.out.println("Random Number Array: ");
        System.out.println(Arrays.toString(randomNumberArray));

        SortUtil.quickSort(randomNumberArray);
        System.out.println("Ordered Array: ");
        System.out.println(Arrays.toString(randomNumberArray));
    }

    @Test
    public void testSort() {

        long beginTime;
        long endTime;

        int[] randomNumberInsertionArray = generalRandomNumberArray(50000);
        int[] randomNumberMergeArray = Arrays.copyOf(randomNumberInsertionArray, randomNumberInsertionArray.length);
        int[] randomNumberBubbleArray = Arrays.copyOf(randomNumberInsertionArray, randomNumberInsertionArray.length);
        int[] randomNumberQuickArray = Arrays.copyOf(randomNumberInsertionArray, randomNumberInsertionArray.length);

        System.out.println("******************************Begin Insertion Sort***********************");
//        System.out.println("Befor Bubble Sort Data: ");
//        System.out.println(Arrays.toString(randomNumberInsertionArray));
        beginTime = System.currentTimeMillis();
        SortUtil.insertionSort(randomNumberInsertionArray);
        endTime = System.currentTimeMillis();
//        System.out.println("After Bubble Sort Data: ");
//        System.out.println(Arrays.toString(randomNumberInsertionArray));
        System.out.println("Insertion Sort Run Time: " + (endTime - beginTime));
        System.out.println("******************************End Insertion Sort***********************");

        System.out.println("******************************Begin Bubble Sort***********************");
//        System.out.println("Befor Bubble Sort Data: ");
//        System.out.println(Arrays.toString(randomNumberBubbleArray));
        beginTime = System.currentTimeMillis();
        SortUtil.bubbleSort(randomNumberBubbleArray);
        endTime = System.currentTimeMillis();
//        System.out.println("After Bubble Sort Data: ");
//        System.out.println(Arrays.toString(randomNumberBubbleArray));
        System.out.println("Bubble Sort Run Time: " + (endTime - beginTime));
        System.out.println("******************************End Bubble Sort***********************");

        System.out.println("******************************Begin Merge Sort***********************");
//        System.out.println("Befor Merge Sort Data: ");
//        System.out.println(Arrays.toString(randomNumberMergeArray));
        beginTime = System.currentTimeMillis();
        SortUtil.mergeSort(randomNumberMergeArray);
        endTime = System.currentTimeMillis();
//        System.out.println("After Merge Sort Data: ");
//        System.out.println(Arrays.toString(randomNumberMergeArray));
        System.out.println("Merge Sort Run Time: " + (endTime - beginTime));

        Assert.assertTrue(Arrays.equals(randomNumberInsertionArray, randomNumberMergeArray));
        System.out.println("******************************End Merge Sort***********************");

        System.out.println("******************************Begin Quick Sort***********************");
//        System.out.println("Befor Quick Sort Data: ");
//        System.out.println(Arrays.toString(randomNumberQuickArray));
        beginTime = System.currentTimeMillis();
        SortUtil.quickSort(randomNumberQuickArray);
        endTime = System.currentTimeMillis();
//        System.out.println("After Quick Sort Data: ");
//        System.out.println(Arrays.toString(randomNumberQuickArray));
        System.out.println("Quick Sort Run Time: " + (endTime - beginTime));

        Assert.assertTrue(Arrays.equals(randomNumberInsertionArray, randomNumberQuickArray));
        System.out.println("******************************End Quick Sort***********************");


    }

    @Test
    public void testFindNthLargestElement() {
        int[] randomNumberArray = generalRandomNumberArray(100);
        System.out.println("Random Number Array: ");
        System.out.println(Arrays.toString(randomNumberArray));

        int nthLargest = 50;
        int nthLargestElement = SortUtil.findNthLargestElement(randomNumberArray, nthLargest);
        System.out.println(String.format("find the %dth largerst element: %d", nthLargest, nthLargestElement));

        SortUtil.quickSort(randomNumberArray);
        System.out.println("Ordered Array: ");
        System.out.println(Arrays.toString(randomNumberArray));

        System.out.println(String.format("The %dth largerst element: %d", nthLargest, randomNumberArray[nthLargest - 1]));
    }

    @Test
    public void testCountingSort() {
        int[] randomNumberArray = generalRandomNumberArray(10);
        System.out.println("Random Number Array: ");
        System.out.println(Arrays.toString(randomNumberArray));

        SortUtil.countingSort(randomNumberArray);
        System.out.println("Ordered Array: ");
        System.out.println(Arrays.toString(randomNumberArray));
    }

    private int[] generalRandomNumberArray(int length) {
        if(length <= 0) {
            throw new IllegalArgumentException("length: " + length);
        }
        int[] randomNumberArray = new int[length];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for(int i = 0; i < randomNumberArray.length; ++i) {
            randomNumberArray[i] = random.nextInt(length + 1);
        }
        return randomNumberArray;
    }
}
