package com.huanghe.autotest.exception;
import lombok.Getter;

/**
 * @author Li Zhiming
 */
public class ResultException extends RuntimeException {
    @Getter
    private int result;

    public ResultException(int result, String message) {
        super(message);
        this.result = result;
    }

    public ResultException(int result, String message, Throwable cause) {
        super(message, cause);
        this.result = result;
    }
}
