package com.zyb.study.algorithm.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class HeadSortTest {

    @Test
    public void test() {

        HeadSort headSort = new HeadSort();

        Integer[] randomNumberArray = generalRandomNumberArray(10);
        System.out.println("Random Number Array: ");
        System.out.println(Arrays.toString(randomNumberArray));
        headSort.sort(randomNumberArray);
        System.out.println("Ordered Array: ");
        System.out.println(Arrays.toString(randomNumberArray));
    }


    private Integer[] generalRandomNumberArray(int length) {
        if(length <= 0) {
            throw new IllegalArgumentException("length: " + length);
        }
        Integer[] randomNumberArray = new Integer[length];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for(int i = 0; i < randomNumberArray.length; ++i) {
            randomNumberArray[i] = random.nextInt(length + 1);
        }
        return randomNumberArray;
    }
}
