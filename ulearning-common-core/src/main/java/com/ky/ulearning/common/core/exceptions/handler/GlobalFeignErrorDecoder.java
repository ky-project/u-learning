package com.ky.ulearning.common.core.exceptions.handler;

import com.ky.ulearning.common.core.constant.MicroErrorCodeEnum;
import com.ky.ulearning.common.core.exceptions.exception.ServerErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author luyuhao
 * @date 19/12/18 22:09
 */
@Slf4j
public class GlobalFeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return new ServerErrorException(MicroErrorCodeEnum.SERVER_DOWN.getMessage());
    }
}
