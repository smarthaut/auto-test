package com.huanghe.autotest.exception;

/**
 * @Time : 2019-12-26 09:23
 * @Author : huanghe
 * @File : BaseResponse
 * @Software: IntelliJ IDEA
 */
public class BaseResponse {
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int FAIL_NO_MANUAL_TRY = -1;
    public static final int LOGIN_FAIL = -2;
    public static final int NOT_FOUND = -3;
    public static final int REPEATED_OPERATION = -4;
    public static final int NOT_PAYED = -5;
    public static final int FAIL_TRANSIENT = -6;
    public static final int Q_NON_ZUIHUIBAO = -100;
    public static final int Q_NOT_IN_DISTRICT = -101;
    int result;
    String message;

    public boolean success() {
        return this.result > 0;
    }

    public boolean fail() {
        return this.result <= 0;
    }

    public BaseResponse() {
    }

    public int getResult() {
        return this.result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseResponse)) {
            return false;
        } else {
            BaseResponse other = (BaseResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getResult() != other.getResult()) {
                return false;
            } else {
                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseResponse;
    }

    public int hashCode() {
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "BaseResponse(result=" + this.getResult() + ", message=" + this.getMessage() + ")";
    }
}
