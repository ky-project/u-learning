package com.ky.ulearning.gateway.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.VerifyCodeUtil;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import com.ky.ulearning.gateway.common.redis.RedisService;
import com.ky.ulearning.gateway.common.security.ImgResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author luyuhao
 * @date 19/11/29 03:10
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RedisService redisService;

    /**
     * 获取验证码
     */
    @ApiOperation(value = "获取验证码", notes = "每次登录前必须先调用该api获取验证码，登录时需带上uuid和用户填写的验证码<br/>返回的img属性在img标签中使用，src=返回的img串")
    @GetMapping(value = "/vCode")
    public ResponseEntity getCode() throws IOException {
        //生成随机字串
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
        String uuid = IdUtil.simpleUUID();
        redisService.saveCode(uuid, verifyCode);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtil.outputImage(w, h, stream, verifyCode);
        try {
            log.debug("生成验证码:" + verifyCode);
            return ResponseEntity.ok(new JsonResult<>(new ImgResult("data:image/gif;base64," + Base64.encode(stream.toByteArray()), uuid), "验证码已生成"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new JsonResult<>(GatewayErrorCodeEnum.CREATE_VERIFY_CODE_FAILED));
        } finally {
            stream.close();
        }
    }
}
