package cn.wolfcode.edums.core.exception;

import cn.wolfcode.edums.core.support.http.HttpCode;

public class InstanceException extends BaseException {
    public InstanceException() {
        super();
    }

    public InstanceException(Throwable t) {
        super(t);
    }

    @Override
    protected HttpCode getCode() {
        return HttpCode.INTERNAL_SERVER_ERROR;
    }
}
