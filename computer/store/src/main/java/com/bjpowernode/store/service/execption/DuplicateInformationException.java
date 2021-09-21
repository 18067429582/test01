package com.bjpowernode.store.service.execption;

/** 电话号码或邮箱地址和修改之前重复 */
public class DuplicateInformationException extends ServiceException {
    public DuplicateInformationException() {
        super();
    }

    public DuplicateInformationException(String message) {
        super(message);
    }

    public DuplicateInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateInformationException(Throwable cause) {
        super(cause);
    }

    protected DuplicateInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
