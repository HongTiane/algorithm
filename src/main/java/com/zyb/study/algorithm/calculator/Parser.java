package com.zyb.study.algorithm.calculator;

import java.util.List;

public interface Parser {
    List<ExpressionUnit> parse(final String expression);
}
