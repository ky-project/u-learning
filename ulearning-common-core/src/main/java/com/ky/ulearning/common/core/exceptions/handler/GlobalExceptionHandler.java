package com.ky.ulearning.common.core.exceptions.handler;

import com.ky.ulearning.common.core.message.JsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @author luyuhao
 * @date 2019/12/6 9:19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 权限不足异常处理
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity accessDeniedException(AccessDeniedException ae) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResult(HttpStatus.FORBIDDEN.value(), "权限不足"));
    }

    /**
     * 参数传递校验异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        return ResponseEntity.badRequest().body(new JsonResult(HttpStatus.BAD_REQUEST.value(),fieldError == null ? null : fieldError.getDefaultMessage()));
    }

    /**
     * - @ExceptionHandler 表示让Spring捕获到所有抛出的Exception异常，并交由这个被注解的方法处理
     * - @ResponseStatus 表示设置状态码
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleException(Exception e) {
        return ResponseEntity.badRequest().body(new JsonResult(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}
