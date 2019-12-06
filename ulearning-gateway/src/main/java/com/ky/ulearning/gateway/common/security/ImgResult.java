package com.ky.ulearning.gateway.common.security;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 验证码对象
 *
 * @author luyuhao
 * @date 19/12/07 02:08
 */
@Data
@AllArgsConstructor
public class ImgResult {

    private String img;

    private String uuid;
}
