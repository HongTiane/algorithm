package com.zyb.study.algorithm.calculator.unit;

import com.zyb.study.algorithm.calculator.ExpressionUnit;

import java.util.Objects;
import java.util.regex.Pattern;

public class Constant extends ExpressionUnit<Double> {
    public static final Pattern CONSTANT_PATTER = Pattern.compile("^(-)?((([1-9]([0-9])*)|(0))(.([0-9])+)?)|(\\()$");

    public Constant(final String svalue) {
        Objects.requireNonNull(svalue);
        if(!CONSTANT_PATTER.matcher(svalue).matches()) {
            throw new IllegalArgumentException("svalue: " + svalue);
        }

        value = Double.parseDouble(svalue);
        type = ExpressionUnitType.CONSTANT;
    }

    public Constant(final double value) {
        this.value = value;
        type = ExpressionUnitType.CONSTANT;
    }
}
