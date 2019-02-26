package com.zyb.study.algorithm.skiplist;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class SkipListTest {

    @Test
    public void testInsert() {
        SkipList<Integer, Integer> integerSkipList = new SkipList<>();
        for(int i = 0; i < 1000; ++i) {
            int randomNumber = generalRandomNumber();
            integerSkipList.insert(randomNumber, randomNumber);
            System.out.println("*************************************************************");
            System.out.println(integerSkipList.print());
            System.out.println("*************************************************************");
        }
    }

    @Test
    public void testFind() {
        SkipList<Integer, Integer> integerSkipList = new SkipList<>();
        for(int i = 0; i < 1000; ++i) {
            int randomNumber = generalRandomNumber();
            integerSkipList.insert(randomNumber, randomNumber);
        }

        Integer target = generalRandomNumber();
        integerSkipList.insert(target, target);
        System.out.println("*************************************************************");
        System.out.println(integerSkipList.print());
        System.out.println("*************************************************************");

        long beginTime = System.currentTimeMillis();
        Integer result = integerSkipList.find(target);
        long endTime = System.currentTimeMillis();
        System.out.println("Find Element: " + target);
        System.out.println("Find Time: " + (endTime - beginTime));
        Assert.assertNotNull(result);
        Assert.assertEquals(result, target);

    }

    @Test
    public void testDelete() {
        SkipList<Integer, Integer> integerSkipList = new SkipList<>();
        for(int i = 0; i < 100; ++i) {
            int randomNumber = generalRandomNumber();
            integerSkipList.insert(randomNumber, randomNumber);
        }

        Integer target = generalRandomNumber();
        integerSkipList.insert(target, target);
        System.out.println("****************************Before Delete Begin*********************************");
        System.out.println(integerSkipList.print());
        System.out.println("****************************Before Delete end***********************************");
        System.out.println("Delete Element: " + target);
        Integer result = integerSkipList.delete(target);
        System.out.println("****************************After Delete Begin*********************************");
        System.out.println(integerSkipList.print());
        System.out.println("****************************After Delete end***********************************");
        Assert.assertNotNull(result);
        Assert.assertEquals(result, target);

    }

    private int generalRandomNumber() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(10000);
    }
}
