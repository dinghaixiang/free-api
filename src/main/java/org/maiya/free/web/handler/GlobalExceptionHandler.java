package org.maiya.free.web.handler;

import org.maiya.free.exception.AppIdInvalidException;
import org.maiya.free.exception.BizBaseException;
import org.maiya.free.exception.DigestInvalidException;
import org.maiya.free.exception.TimestampExpiredException;
import org.maiya.free.model.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器。
 * <p>
 * http://www.ekiras.com/2016/02/how-to-do-exception-handling-in-springboot-rest-application.html
 * <p>
 * Created by wanglei on 16/11/11.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public RespBean handleThrowable(Throwable throwable) {
        return new RespBean("1", "请求失败");
    }

    @ExceptionHandler(value = TimestampExpiredException.class)
    public RespBean handleTimestampExpiredException(TimestampExpiredException tee) {
        return new RespBean("10", "请求超时");
    }

    @ExceptionHandler(value = AppIdInvalidException.class)
    public RespBean handleAppIdInvalidException(AppIdInvalidException aiie) {
        return new RespBean("11", "app_id无效");
    }

    @ExceptionHandler(value = DigestInvalidException.class)
    public RespBean handleDigestInvalidException(DigestInvalidException die) {
        return new RespBean("12", "签名无效");
    }

    @ExceptionHandler(value = BizBaseException.class)
    public RespBean handleBizBaseException(BizBaseException bbe) {
        return new RespBean(bbe.getCode(), bbe.getMessage());
    }

}
