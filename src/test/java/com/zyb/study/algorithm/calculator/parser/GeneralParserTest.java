package com.zyb.study.algorithm.calculator.parser;

import com.zyb.study.algorithm.calculator.exception.ParserException;
import com.zyb.study.algorithm.calculator.ExpressionUnit;
import org.junit.Test;

import java.util.List;

public class GeneralParserTest {

    @Test
    public void test() throws ParserException {
        String expression = "-1+((-1*2)/1)+1.2";

        GeneralParser generalParser = new GeneralParser();

        List<ExpressionUnit> expressions = generalParser.parse(expression);

        System.out.println(expressions);
    }
}
