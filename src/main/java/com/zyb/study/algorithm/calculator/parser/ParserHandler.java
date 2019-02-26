package com.zyb.study.algorithm.calculator.parser;

import com.zyb.study.algorithm.calculator.exception.ParserHandlerException;
import com.zyb.study.algorithm.calculator.ExpressionUnit;

public abstract class ParserHandler {

    public static final char ZERO = '0';
    public static final char NINE = '9';
    public static final char DECIMAL_POINT = '.';

    public static final char MINUS = '-';
//    public static final char PLUS = '+';
//    public static final char MULTIPLY = '*';
//    public static final char DIVIDE = '/';
//    public static final char PERCENT = '%';

    public static final char LEFT_BRACKET = '(';
    public static final char RIGHT_BRACKET = ')';

    public abstract ParserHandlerResult handleParse(final String expression, final int beginIndex) throws ParserHandlerException;

    public static class ParserHandlerResult {
        private final ExpressionUnit expressionUnit;
        private final int length;

        public ParserHandlerResult(ExpressionUnit expressionUnit, int length) {
            this.expressionUnit = expressionUnit;
            this.length = length;
        }

        public ExpressionUnit getExpressionUnit() {
            return expressionUnit;
        }

        public int getLength() {
            return length;
        }
    }
}
