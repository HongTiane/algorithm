package com.zyb.study.algorithm.calculator.parser.handler;

import com.zyb.study.algorithm.calculator.exception.ParserHandlerException;
import com.zyb.study.algorithm.calculator.parser.ParserHandler;
import com.zyb.study.algorithm.calculator.unit.Bracket;
import com.zyb.study.algorithm.calculator.unit.Constant;
import com.zyb.study.algorithm.calculator.ExpressionUnit;

public class LeftBracketParserHandler extends ParserHandler {

    @Override
    public ParserHandlerResult handleParse(String expression, int beginIndex) throws ParserHandlerException {

        StringBuilder sb = new StringBuilder();

        int index = beginIndex;

        char cvalue = expression.charAt(beginIndex);
        if(cvalue == LEFT_BRACKET) {
            return new ParserHandlerResult(new Bracket(ExpressionUnit.ExpressionUnitType.LEFT_BRACKET, "("), 1);
        }

        if(cvalue == MINUS) {
            sb.append(cvalue);
            index++;
        }

        while(index < expression.length()) {
            cvalue = expression.charAt(index++);
            if((cvalue >= ZERO && cvalue <= NINE) || cvalue == DECIMAL_POINT) {
                sb.append(cvalue);
            } else {
                break;
            }
        }
        String svalue = sb.toString();

        boolean matches = Constant.CONSTANT_PATTER.matcher(svalue).matches();
        if(!matches) {
            throw new ParserHandlerException(svalue);
        }

        return new ParserHandlerResult(new Constant(svalue), svalue.length());
    }
}
