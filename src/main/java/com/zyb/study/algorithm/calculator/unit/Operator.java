package com.zyb.study.algorithm.calculator.unit;

import com.zyb.study.algorithm.calculator.ExpressionUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Operator extends ExpressionUnit<String> {
    public static final Pattern OPERAOTR_PATTERN = Pattern.compile("^[\\+-\\\\*/%]$");

    private static final Map<String, OperatorType> OPERATOR_TYPE_MAP = new HashMap<>(5);
    static {
        OPERATOR_TYPE_MAP.put("+", OperatorType.ADDITION);
        OPERATOR_TYPE_MAP.put("-", OperatorType.SUBTRACTION);
        OPERATOR_TYPE_MAP.put("*", OperatorType.MULTIPLICATION);
        OPERATOR_TYPE_MAP.put("/", OperatorType.DIVISION);
        OPERATOR_TYPE_MAP.put("%", OperatorType.MODULUS);
    }

    private final OperatorType operatorType;

    public Operator(final String svalue) {
        Objects.requireNonNull(svalue);
        if(!OPERAOTR_PATTERN.matcher(svalue).matches()) {
            throw new IllegalArgumentException("svalue: " + svalue);
        }
        type = ExpressionUnitType.OPERATOR;
        value = svalue;
        operatorType = OPERATOR_TYPE_MAP.get(svalue);
    }

    @Override
    public ExpressionUnit<Double>  operation(ExpressionUnit<Double> op1, ExpressionUnit<Double> op2) {
        return new Constant(operatorType.operation(op1.getValue(), op2.getValue()));
    }

    @Override
    public int comparePriorityTo(ExpressionUnit eu) {
        return operatorType.comparePriorityTo(((Operator) eu).operatorType);
    }

    public static enum OperatorType {
        ADDITION("+", 1) {
            @Override
            public double operation(double op1, double op2) {
                return op1 + op2;
            }

        },
        SUBTRACTION("-", 1) {
            @Override
            public double operation(double op1, double op2) {
                return op1 - op2;
            }
        },
        MULTIPLICATION("*", 0) {
            @Override
            public double operation(double op1, double op2) {
                return op1 * op2;
            }
        },
        DIVISION("/", 0) {
            @Override
            public double operation(double op1, double op2) {
                return op1 / op2;
            }
        },
        MODULUS("%", 0) {
            @Override
            public double operation(double op1, double op2) {
                return op1 % op2;
            }
        };

        public abstract double operation(double op1, double op2);

        private final String operator;
        private final int priority;

        OperatorType(String operator, int priority) {
            this.operator = operator;
            this.priority = priority;
        }

        public int comparePriorityTo(OperatorType ot) {
            return priority - ot.priority;
        }
    }
}
