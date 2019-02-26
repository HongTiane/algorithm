package com.zyb.study.algorithm.calculator;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class RegularExpressionTest {

    @Test
    public void testNumber() {
        Pattern pattern = Pattern.compile("^[0-9.]+$");
        Assert.assertTrue(pattern.matcher("32").matches());
        Assert.assertTrue(pattern.matcher("32)").matches());
    }

    @Test
    public void testNumberIncludeZero() {

        Pattern pattern = Pattern.compile("^((([1-9]([0-9])*)|(0))(.([0-9])+)?)|(\\()$");

        Assert.assertTrue(pattern.matcher("32").matches());

        Assert.assertTrue(pattern.matcher("1").matches());
        Assert.assertTrue(pattern.matcher("12").matches());
        Assert.assertTrue(pattern.matcher("1.0").matches());
        Assert.assertTrue(pattern.matcher("1.23").matches());
        Assert.assertTrue(pattern.matcher("10.23").matches());

        Assert.assertTrue(pattern.matcher("0").matches());
        Assert.assertTrue(pattern.matcher("0.0").matches());
        Assert.assertTrue(pattern.matcher("0.01").matches());
        Assert.assertTrue(pattern.matcher("0.23").matches());

        Assert.assertFalse(pattern.matcher("00").matches());
        Assert.assertFalse(pattern.matcher("1.").matches());
        Assert.assertFalse(pattern.matcher("1..3").matches());

    }

    @Test
    public void testNumberNotIncludeZero() {

        Pattern pattern = Pattern.compile("^(([1-9]([0-9])*)(.([0-9])+)?)|(0.((([1-9]([0-9])*)+)|(([0])+([1-9])+([0-9])*)))$");

        Assert.assertTrue(pattern.matcher("1").matches());
        Assert.assertTrue(pattern.matcher("12").matches());
        Assert.assertTrue(pattern.matcher("1.0").matches());
        Assert.assertTrue(pattern.matcher("1.23").matches());
        Assert.assertTrue(pattern.matcher("10.23").matches());

        Assert.assertTrue(pattern.matcher("0.000000000000001").matches());
        Assert.assertTrue(pattern.matcher("0.23").matches());

        Assert.assertFalse(pattern.matcher("0").matches());
        Assert.assertFalse(pattern.matcher("0.0").matches());
        Assert.assertFalse(pattern.matcher("00").matches());
        Assert.assertFalse(pattern.matcher("1.").matches());
        Assert.assertFalse(pattern.matcher("1..3").matches());
    }
}
