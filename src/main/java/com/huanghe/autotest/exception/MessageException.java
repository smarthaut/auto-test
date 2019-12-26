package com.huanghe.autotest.exception;

public class MessageException extends RuntimeException {
    private static final long serialVersionUID = -8289499225266956550L;
    private String code;
    private String message;

    public MessageException(String msg) {
        super(msg);
        this.code = "-999";
        this.message = msg;

    }

    public MessageException(String code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
