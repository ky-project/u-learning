package com.ky.ulearning.common.core.validate.validator;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 校验类上下文
 *
 * @author luyuhao
 * @date 20/01/07 01:04
 */
public class ValidatorHolder {

    /** context集合 */
    private List<ValidatorContext> validatorContexts;

    /** 校验结果 */
    private Boolean result;

    /** 若result = true，保存错误信息 */
    private BaseEnum baseEnum;

    /** 初始化 */
    public ValidatorHolder() {
        this.validatorContexts = new ArrayList<>();
        this.result = true;
        this.baseEnum = null;
    }

    /** 校验链 */
    public ValidatorHolder on(Boolean checkResult, BaseEnum baseEnum){
        if(checkResult != null && baseEnum != null){
            addContext(checkResult, baseEnum);
        }
        return this;
    }

    /** 添加待校验属性和错误信息 */
    private void addContext(Boolean checkResult, BaseEnum baseEnum){
        validatorContexts.add(new ValidatorContext(checkResult, baseEnum));
    }

    /** 校验 */
    public ValidatorHolder doValidate(){
        for (ValidatorContext validatorContext : validatorContexts) {
            if(validatorContext.getCheckResult()){
                result = false;
                baseEnum = validatorContext.getBaseEnum();
                break;
            }
        }
        return this;
    }

    public Boolean getResult() {
        return result;
    }

    public BaseEnum getBaseEnum() {
        return baseEnum;
    }
}
