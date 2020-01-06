package com.ky.ulearning.common.core.validate;

import com.ky.ulearning.common.core.validate.validator.ValidatorHolder;

/**
 * 校验builder类
 *
 * @author luyuhao
 * @date 20/01/07 01:02
 */
public class ValidatorBuilder {

    public static ValidatorHolder build(){
        return new ValidatorHolder();
    }
}
