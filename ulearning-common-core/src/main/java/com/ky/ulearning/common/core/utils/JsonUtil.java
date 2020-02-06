package com.ky.ulearning.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import org.apache.commons.io.FilenameUtils;

import java.util.List;
import java.util.Map;

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
     * 将Object 转为JsonString
     *
     * @param object 待转换的对象
     * @return 返回json字符串
     */
    public static String toJsonString(Object object, SerializerFeature... features) {
        return JSONObject.toJSONString(object, features);
    }

    /**
     * String -> T
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

    /**
     * String -> T
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        return JSONObject.parseObject(json, typeReference);
    }

    /**
     * Object -> T
     */
    public static <T> T parseObjectByObject(Object object, Class<T> clazz) {
        String json = toJsonString(object);
        return parseObject(json, clazz);
    }

    /**
     * Object -> T
     */
    public static <T> T parseObjectByObject(Object object, TypeReference<T> typeReference) {
        String json = toJsonString(object);
        return parseObject(json, typeReference);
    }

    /**
     * String -> List<T>
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return JSONObject.parseArray(json, clazz);
    }

    /**
     * T -> Map
     */
    public static <T> Map<String, Object> parseObjectToMap(T t) {
        return JSONObject.parseObject(toJsonString(t));
    }

    public static void main(String[] args){
        FileRecordDto fileRecordDto = new FileRecordDto();
        fileRecordDto.setRecordUrl("http://darren1112.com:8888/group1/M00/00/00/L18Ofl476WWAAxZ0AAB5tEHATng086.jpg");
        fileRecordDto.setRecordName("fh8.jpg");
        fileRecordDto.setRecordSize(31156L);
        fileRecordDto.setRecordType("jpg");
        fileRecordDto.setRecordTable("u_teacher");
        fileRecordDto.setRecordTableId(5L);
        fileRecordDto.setUpdateBy("16620208");
        fileRecordDto.setCreateBy("16620208");
        System.out.println(JsonUtil.parseObjectToMap(fileRecordDto));
    }
}
