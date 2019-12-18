package com.ky.ulearning.common.core.exceptions.handler;

import com.ky.ulearning.common.core.constant.MicroErrorCodeEnum;
import com.ky.ulearning.common.core.exceptions.exception.ServerErrorException;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 服务异常处理
     */
    @ExceptionHandler({ServerErrorException.class, FeignException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity serverErrorException(ServerErrorException se) {
//        log.error(MicroErrorCodeEnum.SERVER_DOWN.getMessage());
        return ResponseEntityUtil.internalServerError(new JsonResult<>(StringUtil.isEmpty(se.getMessage()) ? MicroErrorCodeEnum.SERVER_DOWN : se.getMessage()));
    }

    /**
     * 权限不足异常处理
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity accessDeniedException(AccessDeniedException ae) {
//        log.error(ae.getMessage(), ae);
        return ResponseEntityUtil.forbidden(new JsonResult(MicroErrorCodeEnum.HAS_NO_PERMISSION));
    }

    /**
     * 参数传递校验异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        log.error(ex.getMessage(), ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError != null && StringUtil.isContainChinese(fieldError.getDefaultMessage()) ? fieldError.getDefaultMessage() : null;
        return ResponseEntityUtil.badRequest((message == null ?
                new JsonResult<>(MicroErrorCodeEnum.PARAMETER_ERROR) : new JsonResult<>(HttpStatus.BAD_REQUEST.value(), message)));
    }

    /**
     * - @ExceptionHandler 表示让Spring捕获到所有抛出的Exception异常，并交由这个被注解的方法处理
     * - @ResponseStatus 表示设置状态码
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleException(Exception e) {
//        log.error(e.getMessage(), e);
        String message = StringUtil.isContainChinese(e.getMessage()) ? e.getMessage() : null;
        return ResponseEntityUtil.badRequest(message == null ?
                new JsonResult<>(MicroErrorCodeEnum.SYSTEM_ERROR) : new JsonResult<>(HttpStatus.BAD_REQUEST.value(), message));
    }
}
