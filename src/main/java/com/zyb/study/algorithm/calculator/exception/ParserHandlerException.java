package com.zyb.study.algorithm.calculator.exception;

public class ParserHandlerException extends Exception {

    public ParserHandlerException() {
    }

    public ParserHandlerException(String message) {
        super(message);
    }

    public ParserHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserHandlerException(Throwable cause) {
        super(cause);
    }
}
