package com.zyb.study.algorithm.calculator;

public abstract class ExpressionUnit<T> {

    protected ExpressionUnitType type;
    protected T value;

    public ExpressionUnitType getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    public ExpressionUnit<Double> operation(ExpressionUnit<Double> op1, ExpressionUnit<Double> op2) {
        throw new UnsupportedOperationException();
    }

    public int comparePriorityTo(ExpressionUnit eu) {
        throw new UnsupportedOperationException();
    }

    public static enum ExpressionUnitType {
        CONSTANT, OPERATOR, LEFT_BRACKET, RIGHT_BRACKET;
    }
}
