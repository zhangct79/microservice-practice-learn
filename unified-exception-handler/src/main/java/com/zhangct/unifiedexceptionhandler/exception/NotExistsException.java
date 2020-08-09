package com.zhangct.unifiedexceptionhandler.exception;

public class NotExistsException extends RuntimeException {
    public NotExistsException() {
    }

    public NotExistsException(String msg) {
        super(msg);
    }
}
