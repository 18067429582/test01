package com.bjpowernode.store.service.execption;

/** 用户数据不存在的异常 */
public class CommodityRepeatException extends ServiceException {
    public CommodityRepeatException() {
        super();
    }

    public CommodityRepeatException(String message) {
        super(message);
    }

    public CommodityRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommodityRepeatException(Throwable cause) {
        super(cause);
    }

    protected CommodityRepeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
