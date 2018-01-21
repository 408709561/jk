package com.github.wxiaoqi.security.common.handler;

import com.github.wxiaoqi.core.exception.BaseException;
import com.github.wxiaoqi.security.common.exception.auth.ClientTokenException;
import com.github.wxiaoqi.security.common.exception.auth.NonLoginException;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常拦截处理器
 * @author ace
 * @version 2017/9/8
 */
@ControllerAdvice("com.github.wxiaoqi.security")
@ResponseBody
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public BaseResponse baseExceptionHandler(HttpServletResponse response, BaseException ex) {
        logger.error(ex.getMessage(),ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse otherExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(ClientTokenException.class)
    public BaseResponse clientTokenExceptionHandler(HttpServletResponse response, ClientTokenException ex) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(NonLoginException.class)
    public BaseResponse userTokenExceptionHandler(HttpServletResponse response, NonLoginException ex) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(HttpServletResponse response, BusinessException ex) {
        response.setStatus(HttpStatus.METHOD_FAILURE.value());
        logger.info(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }
}
