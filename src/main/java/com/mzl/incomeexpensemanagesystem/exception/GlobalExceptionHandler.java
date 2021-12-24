package com.mzl.incomeexpensemanagesystem.exception;

import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName :   GlobalExceptionHandler
 * @Description: 全局异常捕获处理类
 * @Author: v_ktlema
 * @CreateDate: 2021/12/22 20:44
 * @Version: 1.0
 */
@RestControllerAdvice   //相当于@RestControllerAdvice+@ResponseBody
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获处理自定义异常 可以多个 @ExceptionHandler({})
     * @param request  request
     * @param e        exception
     * @param response response
     * @return 响应结果
     */
    //@ResponseBody   //@RestControllerAdvice就不用加了，如是@ControllerAdvice就要
    @ExceptionHandler(CustomException.class)
    //@ExceptionHandler({CustomException.class}) //多个捕获异常
    public ErrorRetResult customExceptionHandler(Exception e, HttpServletResponse response, HttpServletRequest request) {
        CustomException exception = (CustomException) e;
        return ErrorRetResult.error(exception.getCode(), exception.getMessage());
    }

    /**
     * 捕获处理参数解析失败异常 HttpMessageNotReadableException
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorRetResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + ":参数解析失败", e);
        return ErrorRetResult.error(e.getMessage());
    }

    /**
     * 捕获处理空指针的异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    public ErrorRetResult nullPointerExceptionHandler(NullPointerException e, HttpServletRequest request){
        logger.error("发生空指针异常！原因是:", e);
        return ErrorRetResult.error(RetCodeEnum.NULL_POINT_EXCEPTION);
    }

    /**
     * 捕获处理 RuntimeException 异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ErrorRetResult handleHttpMessageNotReadableException(RuntimeException e, HttpServletRequest request) {
        return ErrorRetResult.error(e.getMessage());
    }

}
