package com.zyb.study.algorithm.calculator.parser;

import com.zyb.study.algorithm.calculator.Parser;
import com.zyb.study.algorithm.calculator.exception.ParserException;
import com.zyb.study.algorithm.calculator.exception.ParserHandlerException;
import com.zyb.study.algorithm.calculator.parser.handler.ConstantAndRightBracketParserHandler;
import com.zyb.study.algorithm.calculator.parser.handler.LeftBracketParserHandler;
import com.zyb.study.algorithm.calculator.parser.handler.OperatorParserHandler;
import com.zyb.study.algorithm.calculator.unit.Constant;
import com.zyb.study.algorithm.calculator.ExpressionUnit;

import java.util.*;

import static com.zyb.study.algorithm.calculator.parser.ParserHandler.*;
import static com.zyb.study.algorithm.calculator.ExpressionUnit.*;

public class GeneralParser implements Parser {
    private static final Map<ExpressionUnitType, ParserHandler> EXPRESSION_UNIT_TYPE_PARSER_HANDLER_MAP = new HashMap<>(4);
    static {
        EXPRESSION_UNIT_TYPE_PARSER_HANDLER_MAP.put(ExpressionUnitType.CONSTANT, new ConstantAndRightBracketParserHandler());
        EXPRESSION_UNIT_TYPE_PARSER_HANDLER_MAP.put(ExpressionUnitType.OPERATOR, new OperatorParserHandler());
        EXPRESSION_UNIT_TYPE_PARSER_HANDLER_MAP.put(ExpressionUnitType.LEFT_BRACKET, new LeftBracketParserHandler());
        EXPRESSION_UNIT_TYPE_PARSER_HANDLER_MAP.put(ExpressionUnitType.RIGHT_BRACKET, new ConstantAndRightBracketParserHandler());
    }
    @Override
    public List<ExpressionUnit> parse(String expression) throws ParserException {

        Objects.requireNonNull(expression);
        if(expression.length() == 0) {
            throw new IllegalArgumentException("expression length is 0.");
        }

        String pretreatExpression = pretreat(expression);
        int offset = 2;
        if(pretreatExpression.charAt(1) ==  '-') {
            offset = 1;
        }
        String originalExpression = pretreatExpression.substring(offset);

        LinkedList<ExpressionUnit> expressionUnits = new LinkedList<>();
        Stack<ExpressionUnit> leftBrackets = new Stack<>();

        expressionUnits.add(new Constant("0"));
        ParserHandler nextParserHandler = EXPRESSION_UNIT_TYPE_PARSER_HANDLER_MAP.get(ExpressionUnitType.CONSTANT);
        int index = 1;

        int pretreatExpressionLastIndex = pretreatExpression.length() - 1;
        for(; index <= pretreatExpressionLastIndex;) {
            try {
                ParserHandlerResult parserHandlerResult = nextParserHandler.handleParse(pretreatExpression, index);
                ExpressionUnit expressionUnit = parserHandlerResult.getExpressionUnit();
                if(expressionUnit.getType() == ExpressionUnitType.LEFT_BRACKET) {
                    leftBrackets.push(expressionUnit);
                } else if(expressionUnit.getType() == ExpressionUnitType.RIGHT_BRACKET) {
                    if(leftBrackets.empty()) {
                        throw new ParserException(String.format("Expression %s Error: error index is [%d], error is %s", originalExpression, index - offset, ")"));
                    }
                    leftBrackets.pop();
                }

                expressionUnits.add(expressionUnit);
                index += parserHandlerResult.getLength();
                nextParserHandler = EXPRESSION_UNIT_TYPE_PARSER_HANDLER_MAP.get(expressionUnit.getType());
            } catch (ParserHandlerException e) {
                throw new ParserException(String.format("Expression %s Error: error index is [%d], error is %s", originalExpression, index - offset, e.getMessage()));
            }
        }

        if(!leftBrackets.empty()) {
            throw new ParserException(String.format("Expression %s Error: error is missingbraces", originalExpression));
        }
        ExpressionUnit LastExpressionUnits = expressionUnits.getLast();
        if(LastExpressionUnits.getType() == ExpressionUnitType.OPERATOR) {
            throw new ParserException(String.format("Expression %s Error: error is lack of expression", originalExpression));
        }
        return expressionUnits;
    }
    
    private String pretreat(String expression) {

        String[] clearSpaceExpressions = expression.split(" ");
        StringBuilder sb = new StringBuilder();
        char firstChar = expression.trim().charAt(0);
        if(firstChar == MINUS) {
            sb.append("0");
        } else {
            sb.append("0+");
        }
        for(String clearSpaceExpression : clearSpaceExpressions) {
            sb.append(clearSpaceExpression);
        }
        return sb.toString();
    }
}
