package com.zyb.study.algorithm.calculator.unit;

import com.zyb.study.algorithm.calculator.ExpressionUnit;

import java.util.Objects;

public class Bracket extends ExpressionUnit<String> {
    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";

    public Bracket(final ExpressionUnitType type, final String svalue) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(svalue);

        if((type == ExpressionUnitType.LEFT_BRACKET && LEFT_BRACKET.equals(svalue))
                || (type == ExpressionUnitType.RIGHT_BRACKET && RIGHT_BRACKET.equals(svalue))) {
            this.type = type;
            value = svalue;
        } else {
            throw new IllegalArgumentException("type: " + type + ", svalue: " + svalue);
        }
    }
}
