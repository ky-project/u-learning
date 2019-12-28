package com.ky.ulearning.gateway.common.redis;

/**
 * redis service 接口类
 *
 * @author luyuhao
 * @date 19/12/07 02:02
 */
public interface RedisService {

    /**
     * 保存数据
     * @param key 键
     * @param val 值
     */
    void saveCode(String key, Object val);

    /**
     * 查询验证码的值
     * @param key 键
     * @return 返回查询到的字符串
     */
    String getCodeVal(String key);

    /**
     * delete
     * @param key 键
     */
    void delete(String key);
}
