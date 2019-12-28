package com.ky.ulearning.common.core.exceptions.exception;

/**
 * 服务异常
 *
 * @author luyuhao
 * @date 2019/12/7 10:52
 */
public class ServerErrorException extends RuntimeException {

    public ServerErrorException() {
    }

    public ServerErrorException(String msg) {
        super(msg);
    }
}
