package com.lake.waterlake.exception;

/**
 * 自定义的异常基类
 *
 * @author
 * @date 2016-8-31
 */
public class BaseException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -308096936294821197L;

    @SuppressWarnings("unused")
    private int code;

    public BaseException(int code) {
        super();
        this.code = code;
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BaseException(int code, String msg, Throwable throwable) {
        super(msg, throwable);
        this.code = code;
    }

    public BaseException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }
}
