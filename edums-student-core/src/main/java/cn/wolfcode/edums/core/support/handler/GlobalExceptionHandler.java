package cn.wolfcode.edums.core.support.handler;

import cn.wolfcode.edums.core.exception.BaseException;
import cn.wolfcode.edums.core.support.http.HttpCode;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Leon
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger();
    private static final String TAG = "[global exception]";

    @ExceptionHandler
    public void handle(Exception e, HttpServletResponse resp) throws IOException {
        HttpCode code = HttpCode.INTERNAL_SERVER_ERROR;
        String msg = code.msg();
        if (e instanceof IllegalArgumentException) {
            code = HttpCode.BAD_REQUEST;
            msg = e.getMessage();
            logger.warn(String.format("%s Illegal Argument: %s", TAG, e.getMessage()), e);
        } else if (e instanceof AuthorizationException) {
            code = HttpCode.FORBIDDEN;
            msg = code.msg();
            logger.warn(String.format("%s Not Authorized: %s", TAG, e.getMessage()), e);
        } else if (e instanceof BaseException) {
            msg = e.getMessage();
            logger.warn(String.format("%s Business Exception: %s", TAG, e.getMessage()), e);
        } else {
            logger.error(String.format("%s System Exception: %s", TAG, e.getMessage()), e);
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        resp.setStatus(HttpStatus.OK.value());

        JSONObject ret = new JSONObject();
        ret.put("code", code.value());
        ret.put("msg", msg);
        ret.put("timestamp", System.currentTimeMillis());
        resp.getWriter().write(ret.toJSONString());
    }
}
