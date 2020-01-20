package com.ky.ulearning.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * json工具类
 *
 * @author luyuhao
 * @since 19/12/11 01:36
 */
public class JsonUtil {

    private static final SerializerFeature[] FEATURES = {
            SerializerFeature.SkipTransientField,
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.WriteNullStringAsEmpty
    };

    /**
     * 将Object 转为JsonString，并且不忽略null
     *
     * @param object 待转换的对象
     * @return 返回json字符串
     */
    public static String toJsonString(Object object) {
        return JSONObject.toJSONString(object, FEATURES);
    }

    /**
     * String -> T
     */
    public static <T> T parseObject(String json, Class<T> clazz){
        return JSONObject.parseObject(json, clazz);
    }

    /**
     * Object -> T
     */
    public static <T> T parseObjectByObject(Object object, Class<T> clazz){
        String json = toJsonString(object);
        return parseObject(json, clazz);
    }

    /**
     * String -> List<T>
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz){
        return JSONObject.parseArray(json, clazz);
    }
}
