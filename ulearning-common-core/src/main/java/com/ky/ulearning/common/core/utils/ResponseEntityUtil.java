package com.ky.ulearning.common.core.utils;

import org.springframework.http.ResponseEntity;

/**
 * 响应对象工具类
 *
 * @author luyuhao
 * @date 19/12/14 16:49
 */
public class ResponseEntityUtil {

    public static <T> ResponseEntity badRequest(T body){
        return ResponseEntity.badRequest().body(body);
    }

    public static <T> ResponseEntity ok(T body){
        return ResponseEntity.ok(body);
    }
}
