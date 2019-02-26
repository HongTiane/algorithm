package com.zyb.study.algorithm.array;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArrayTest {

    @Test
    public void test() {
        Array<Integer> integerArray = new Array<>();

        for(int i = 0; i < 20; i++) {
            integerArray.add(i);
        }

        Assert.assertEquals(integerArray.size(), 20);

//        Assert.assertEquals(integerArray.toString(), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
    }
}
