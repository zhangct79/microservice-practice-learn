package com.zhangct.unifiedexceptionhandler.base;

public enum ResultEnum {
    UNKNOW(-1,"未知错误"),
    NOT_EXISTS(1,"查找的资源不存在"),
    SUCCESS(0,"操作成功");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ResultEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
