package com.ky.ulearning.common.core.exceptions.exception;

/**
 * 实体类已存在异常
 *
 * @author luyuhao
 * @date 19/12/09 03:48
 */
public class EntityExistException extends RuntimeException {
    public EntityExistException(String message) {
        super(message + "已存在");
    }
}

