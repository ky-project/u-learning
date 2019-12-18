package com.ky.ulearning.common.core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * 响应对象工具类
 *
 * @author luyuhao
 * @date 19/12/14 16:49
 */
public class ResponseEntityUtil {

    public static <T> ResponseEntity<T> badRequest(T body){
        return ResponseEntity.badRequest()
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(body);
    }

    public static <T> ResponseEntity<T> ok(T body){
        return ResponseEntity.ok()
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(body);
    }

    public static <T> ResponseEntity<T> custom(HttpStatus status, T body){
        return ResponseEntity.status(status)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(body);
    }

}
