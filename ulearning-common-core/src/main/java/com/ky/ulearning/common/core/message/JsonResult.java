package com.ky.ulearning.common.core.message;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 统一返回json格式
 *
 * @author luyuhao
 * @since 2019/12/5 9:36
 */
@ApiModel("统一响应类")
@Data
@Accessors(chain = true)
public class JsonResult<T> implements Serializable {

    private static final Integer SUCCESS_CODE = HttpStatus.OK.value();
    private static final String SUCCESS_MESSAGE = "请求成功";

    /**
     * http状态码
     */
    @ApiModelProperty("http状态码")
    private Integer code;
    /**
     * 返回信息
     */
    @ApiModelProperty("返回信息")
    private String message;
    /**
     * 数据
     */
    @ApiModelProperty("返回数据")
    private T data;

    public JsonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <C> JsonResult<C> build(Integer code, String message, C data) {
        return new JsonResult<>(code, message, data);
    }

    public static <C> JsonResult<C> buildData(C data) {
        return build(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <C> JsonResult<C> buildMsg(String message) {
        return build(SUCCESS_CODE, message, null);
    }

    public static <C> JsonResult<C> buildDataMsg(C data, String message) {
        return build(SUCCESS_CODE, message, data);
    }

    public static <C> JsonResult<C> buildErrorMsg(Integer code, String message) {
        return build(code, message, null);
    }

    public static <C> JsonResult<C> buildErrorEnum(BaseEnum baseEnum) {
        return build(baseEnum.getCode(), baseEnum.getMessage(), null);
    }
}
