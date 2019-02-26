package com.zyb.study.algorithm.calculator;

import com.zyb.study.algorithm.calculator.parser.GeneralParser;

import java.util.*;

import static com.zyb.study.algorithm.calculator.ExpressionUnit.*;

public class Calculator {

    private Parser parser;

    private Stack<ExpressionUnit<Double>> constantStack = new Stack<>();
    private Stack<ExpressionUnit> operatorAndBraketStack = new Stack<>();

    public Calculator() {
        parser = new GeneralParser();
    }

    public double execute(final String expression) {
        List<ExpressionUnit> expressionUnits = parser.parse(expression);
        for(int i = 0; i < expressionUnits.size(); i++) {
            ExpressionUnit expressionUnit = expressionUnits.get(i);
            switch(expressionUnit.getType()) {
                case CONSTANT:
                    constantStack.push(expressionUnit);
                    break;
                case OPERATOR:

                    if(operatorAndBraketStack.isEmpty()) {
                        operatorAndBraketStack.push(expressionUnit);
                        break;
                    }
                    ExpressionUnit prevExpressionUnit = operatorAndBraketStack.peek();
                    if(prevExpressionUnit.getType() == ExpressionUnitType.OPERATOR) {
                        if(prevExpressionUnit.comparePriorityTo(expressionUnit) <= 0) {
                            operatorAndBraketStack.pop();
                            operatorAndBraketStack.push(expressionUnit);
                            ExpressionUnit op2 = constantStack.pop();
                            ExpressionUnit op1 = constantStack.pop();
                            constantStack.push(prevExpressionUnit.operation(op1, op2));
                        } else {
                            ExpressionUnit nextExpressionUnit = expressionUnits.get(++i);
                            if(nextExpressionUnit.getType() == ExpressionUnitType.LEFT_BRACKET) {
                                operatorAndBraketStack.push(expressionUnit);
                                operatorAndBraketStack.push(nextExpressionUnit);
                                break;
                            }
                            constantStack.push(expressionUnit.operation(constantStack.pop(), nextExpressionUnit));
                        }
                    } else {
                        operatorAndBraketStack.push(expressionUnit);
                    }
                    break;
                case LEFT_BRACKET:
                    operatorAndBraketStack.push(expressionUnit);
                    break;
                case RIGHT_BRACKET:
                    while(true) {
                        ExpressionUnit operator = operatorAndBraketStack.pop();
                        if(operator.getType() == ExpressionUnitType.LEFT_BRACKET) {
                            break;
                        }
                        ExpressionUnit op2 = constantStack.pop();
                        ExpressionUnit op1 = constantStack.pop();
                        constantStack.push(operator.operation(op1, op2));
                    }
                    break;
            }
        }

        while(!operatorAndBraketStack.isEmpty()) {
            ExpressionUnit operator = operatorAndBraketStack.pop();
            ExpressionUnit op2 = constantStack.pop();
            ExpressionUnit op1 = constantStack.pop();
            constantStack.push(operator.operation(op1, op2));
        }

        return constantStack.pop().getValue();
    }

}
