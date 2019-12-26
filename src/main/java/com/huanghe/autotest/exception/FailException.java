package com.huanghe.autotest.exception;


/**
 * @author Nie Shiqiang
 * @Date 2018/3/6
 */
public class FailException extends ResultException {

    public FailException(String message) {
        super(BaseResponse.FAIL, message);
    }

    public FailException(String message, Throwable cause) {
        super(BaseResponse.FAIL, message, cause);
    }
}
