package cn.wolfcode.edums.core.exception;

import cn.wolfcode.edums.core.support.http.HttpCode;

public class LoginException extends BaseException {
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Exception e) {
        super(message, e);
    }

    @Override
    protected HttpCode getCode() {
        return HttpCode.LOGIN_FAIL;
    }
}