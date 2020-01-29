package com.ky.ulearning.common.core.exceptions.handler;

import com.ky.ulearning.common.core.constant.MicroErrorCodeEnum;
import com.ky.ulearning.common.core.exceptions.exception.BaseException;
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
import org.springframework.web.multipart.MultipartException;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

/**
 * @author luyuhao
 * @since 2019/12/6 9:19
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 服务异常处理
     */
    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleServerErrorException(ServerErrorException se) {
        log.error("系统捕捉ServerErrorException异常并处理 ==> " + MicroErrorCodeEnum.SERVER_DOWN.getMessage(), se);
        String message = StringUtil.isContainChinese(se.getMessage()) ? se.getMessage() : null;
        return ResponseEntityUtil.internalServerError(JsonResult.buildErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Optional.ofNullable(message).orElse(MicroErrorCodeEnum.SERVER_DOWN.getMessage())));
    }

    /**
     * 权限不足异常处理
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(AccessDeniedException ae) {
        log.error("系统捕捉AccessDeniedException异常并处理 ==> " + ae.getMessage(), ae);
        String message = StringUtil.isContainChinese(ae.getMessage()) ? ae.getMessage() : null;
        return ResponseEntityUtil.forbidden(JsonResult.buildErrorMsg(HttpStatus.FORBIDDEN.value(),
                Optional.ofNullable(message).orElse(MicroErrorCodeEnum.HAS_NO_PERMISSION.getMessage())));
    }

    /**
     * 文件上传异常处理
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity handleMultipartException(Throwable t){
        return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum(MicroErrorCodeEnum.FILE_UPLOAD_ERROR));
    }

    /**
     * 处理系统自定义异常
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleBaseException(BaseException e) {
        String message = StringUtil.isContainChinese(e.getMessage()) ? e.getMessage() : null;
        return ResponseEntityUtil.custom(HttpStatus.resolve(e.getStatus()), JsonResult.buildErrorMsg(e.getStatus(),
                Optional.ofNullable(message).orElse(MicroErrorCodeEnum.OPERATE_ERROR.getMessage())));
    }

    /**
     * Throwable
     * 接收非系统预测内的异常
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity handleThrowable(Throwable e) {
        log.error("系统捕捉Throwable异常并处理 ==> " + e.getMessage(), e);
        String message = StringUtil.isContainChinese(e.getMessage()) ? e.getMessage() : null;
        return ResponseEntityUtil.internalServerError(JsonResult.buildErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Optional.ofNullable(message).orElse(MicroErrorCodeEnum.SYSTEM_ERROR.getMessage())));
    }
}
