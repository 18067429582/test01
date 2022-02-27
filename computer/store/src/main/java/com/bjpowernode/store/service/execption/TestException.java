package com.bjpowernode.store.service.execption;

/** 非法访问的异常 */
public class TestException extends ServiceException {
    public TestException() {
        super();
    }

    public TestException(String message) {
        super(message);
    }

    public TestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestException(Throwable cause) {
        super(cause);
    }

    protected TestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
