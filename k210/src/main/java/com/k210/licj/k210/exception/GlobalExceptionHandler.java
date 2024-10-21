package com.k210.licj.k210.exception;

import com.k210.licj.k210.response.JsonResult;
import com.k210.licj.k210.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public JsonResult doHandleServiceException(ServiceException ex){
        log.error("ServiceException is: " + ex.getMessage());
        return new JsonResult(ex.getStatusCode());
    }


    @ExceptionHandler(value = ServletException.class)
    public JsonResult doHandleServletException(ServletException ex){
        log.error("ServletException is: " + ex.getMessage());
        return new JsonResult(StatusCode.NOT_LOGIN);
    }


    @ExceptionHandler
    public JsonResult doHandleIllegalArgumentException(IllegalArgumentException ex){
        log.error("IllegalArgumentException is: " + ex.getMessage());
        return new JsonResult(StatusCode.OPERATION_FAILED, ex.getMessage());
    }

    @ExceptionHandler
    public JsonResult doHandleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String message = ex.getFieldError().getDefaultMessage();
        return new JsonResult(StatusCode.VALIDATE_ERROR.getCode(), message);
    }


}
