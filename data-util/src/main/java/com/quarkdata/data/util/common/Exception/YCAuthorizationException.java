package com.quarkdata.data.util.common.Exception;

/**
 * Created by xiexl on 2018/1/11.
 * 文档操作权限异常
 */
public class YCAuthorizationException extends  RuntimeException{
    /**
     * Creates a new AuthorizationException.
     */
    public YCAuthorizationException() {
        super();
    }

    /**
     * Constructs a new AuthorizationException.
     *
     * @param message the reason for the exception
     */
    public YCAuthorizationException(String message) {
        super(message);
    }

    /**
     * Constructs a new AuthorizationException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public YCAuthorizationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new AuthorizationException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public YCAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
