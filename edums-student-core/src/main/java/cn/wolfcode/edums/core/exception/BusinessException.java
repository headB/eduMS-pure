package cn.wolfcode.edums.core.exception;

import cn.wolfcode.edums.core.support.http.HttpCode;

/**
 * @author Leon
 * @date 2020-01-07
 */
public class BusinessException extends BaseException {

    public BusinessException(Throwable ex) {
        super(ex);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable ex) {
        super(message, ex);
    }

    @Override
    protected HttpCode getCode() {
        return HttpCode.BUSINESS_EXCEPTION;
    }
}
