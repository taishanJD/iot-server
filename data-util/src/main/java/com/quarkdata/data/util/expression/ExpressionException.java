package com.quarkdata.data.util.expression;

/**
 * Created by TS-Ysh on 2018-02-06.
 */
public class ExpressionException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExpressionException() {
        super();
    }

    public ExpressionException(String msg) {
        super(msg);
    }

    public ExpressionException(String msg, Throwable cause) {
        super(msg,cause);
    }

    public ExpressionException(Throwable cause) {
        super(cause);
    }
}
