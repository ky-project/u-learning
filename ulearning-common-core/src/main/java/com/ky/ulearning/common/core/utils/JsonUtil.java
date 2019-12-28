package com.ky.ulearning.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author luyuhao
 * @date 19/12/11 01:36
 */
public class JsonUtil {

    /**
     * 将Object 转为JsonString，并且不忽略null
     *
     * @param object 待转换的对象
     * @return 返回json字符串
     */
    public static String toJsonString(Object object) {
        return JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue);
    }
}
