package com.ky.ulearning.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyuhao
 * @date 19/11/29 03:10
 */
@RestController
public class TmpController {

    @GetMapping("/test")
    public String test(){
        return "hello";
    }
}
