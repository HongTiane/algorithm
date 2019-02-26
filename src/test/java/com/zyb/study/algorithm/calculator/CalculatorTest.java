package com.zyb.study.algorithm.calculator;

import org.junit.Test;

public class CalculatorTest {
    @Test
    public void test() {

        String expression = "-100+((-1*2)/1)+2*(2*2)/2";

        Calculator calculator = new Calculator();

        double result = calculator.execute(expression);

        System.out.println(result);
    }
}
