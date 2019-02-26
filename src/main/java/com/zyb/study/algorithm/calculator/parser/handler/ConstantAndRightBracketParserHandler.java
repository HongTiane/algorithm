package com.zyb.study.algorithm.calculator.parser.handler;

import com.zyb.study.algorithm.calculator.exception.ParserHandlerException;
import com.zyb.study.algorithm.calculator.parser.ParserHandler;
import com.zyb.study.algorithm.calculator.unit.Bracket;
import com.zyb.study.algorithm.calculator.ExpressionUnit;
import com.zyb.study.algorithm.calculator.unit.Operator;

public class ConstantAndRightBracketParserHandler extends ParserHandler {

    @Override
    public ParserHandlerResult handleParse(String expression, int beginIndex) throws ParserHandlerException {

        char cvalue = expression.charAt(beginIndex);
        if(cvalue == RIGHT_BRACKET) {
            return new ParserHandlerResult(new Bracket(ExpressionUnit.ExpressionUnitType.RIGHT_BRACKET, ")"), 1);
        }

        String svalue = expression.substring(beginIndex, beginIndex + 1);
        boolean matches = Operator.OPERAOTR_PATTERN.matcher(svalue).matches();
        if(!matches) {
            throw new ParserHandlerException(svalue);
        }
        return new ParserHandlerResult(new Operator(svalue), 1);
    }
}
