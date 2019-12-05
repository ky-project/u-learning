package com.ky.ulearning.common.core.message;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import com.ky.ulearning.common.core.exceptions.enums.BaseErrorCodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 统一返回json格式
 *
 * @author luyuhao
 * @date 2019/12/5 9:36
 */
@Data
@Accessors(chain = true)
public class JsonResult<T> implements Serializable {

    private static final Integer SUCCESS_CODE = HttpStatus.OK.value();
    private static final String SUCCESS_MESSAGE = "请求成功";

    /**
     * http状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    public JsonResult() {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
    }

    public JsonResult(T data) {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
        this.data = data;
    }

    public JsonResult(BaseEnum baseEnum) {
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public JsonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
