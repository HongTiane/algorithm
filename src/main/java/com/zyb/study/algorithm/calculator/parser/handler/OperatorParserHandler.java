package com.zyb.study.algorithm.calculator.parser.handler;

import com.zyb.study.algorithm.calculator.exception.ParserHandlerException;
import com.zyb.study.algorithm.calculator.parser.ParserHandler;
import com.zyb.study.algorithm.calculator.unit.Bracket;
import com.zyb.study.algorithm.calculator.unit.Constant;
import com.zyb.study.algorithm.calculator.ExpressionUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class OperatorParserHandler extends ParserHandler {

    private static final Pattern CONSTANT_PATTERN = Pattern.compile("^((([1-9]([0-9])*)|(0))(.([0-9])+)?)|(\\()$");
    private static final Pattern NON_ZERO_CONSTANT_PATTERN = Pattern.compile("^(([1-9]([0-9])*)(.([0-9])+)?)|(0.((([1-9]([0-9])*)+)|(([0])+([1-9])+([0-9])*)))|(\\()$");

    private static final Map<Character, Pattern> OPERATOR_PATTERN_MAP = new HashMap<>(5);
    static {
        OPERATOR_PATTERN_MAP.put('+', CONSTANT_PATTERN);
        OPERATOR_PATTERN_MAP.put('-', CONSTANT_PATTERN);
        OPERATOR_PATTERN_MAP.put('*', CONSTANT_PATTERN);

        OPERATOR_PATTERN_MAP.put('/', NON_ZERO_CONSTANT_PATTERN);
        OPERATOR_PATTERN_MAP.put('%', NON_ZERO_CONSTANT_PATTERN);
    }

    @Override
    public ParserHandlerResult handleParse(final String expression, final int beginIndex) throws ParserHandlerException {

        char cvalue = expression.charAt(beginIndex);
        if(cvalue == LEFT_BRACKET) {
            return new ParserHandlerResult(new Bracket(ExpressionUnit.ExpressionUnitType.LEFT_BRACKET, "("), 1);
        }

        StringBuilder sb = new StringBuilder();
        int index = beginIndex;
        while((cvalue >= ZERO && cvalue <= NINE) || cvalue == DECIMAL_POINT) {
            sb.append(cvalue);
            if(++index >= expression.length()) {
                break;
            }
            cvalue = expression.charAt(index);
        }
        String svalue = sb.toString();

        Pattern pattern = OPERATOR_PATTERN_MAP.get(expression.charAt(beginIndex - 1));
        boolean matches = pattern.matcher(svalue).matches();
        if(!matches) {
            throw new ParserHandlerException(svalue);
        }
        return new ParserHandlerResult(new Constant(svalue), svalue.length());
    }
}
