package com.bjpowernode.store.service.execption;

/** 用户数据不存在的异常 */
public class DeleteFavorityException extends ServiceException {
    public DeleteFavorityException() {
        super();
    }

    public DeleteFavorityException(String message) {
        super(message);
    }

    public DeleteFavorityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteFavorityException(Throwable cause) {
        super(cause);
    }

    protected DeleteFavorityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
