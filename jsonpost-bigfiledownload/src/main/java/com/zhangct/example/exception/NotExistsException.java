package com.zhangct.example.exception;

/**
 * @author zhangct
 * @date 20/8/7
 */


public class NotExistsException extends Exception{
    static final long serialVersionUID = -7034897190745766939L;
    public NotExistsException() {

    }

    public NotExistsException(String msg) {
        super(msg);
    }
}

