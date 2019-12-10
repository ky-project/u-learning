package com.ky.ulearning.gateway.common.exception;

import javax.security.sasl.AuthenticationException;

/**
 * token校验异常
 *
 * @author luyuhao
 * @date 2019/12/10 9:30
 */
public class JwtTokenException extends AuthenticationException {

    public JwtTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtTokenException(String msg) {
        super(msg);
    }
}
