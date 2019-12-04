package com.ky.ulearning.system.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyuhao
 * @date 19/12/05 01:57
 */
@RestController
@RequestMapping("/auth")
public class AuthLoginController {

    @GetMapping("/test")
    public String test01(){
        return "hello";
    }
}
