package com.huanghe.autotest.util;

import com.huanghe.autotest.exception.MessageException;

public class ExceptionBuilder {
    public static MessageException create(String message) {
        return new MessageException(message);
    }
    public static MessageException create(String code,String message) {
        return new MessageException(code,message);
    }
}
