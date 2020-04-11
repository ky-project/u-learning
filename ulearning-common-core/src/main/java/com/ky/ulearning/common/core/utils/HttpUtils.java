package com.ky.ulearning.common.core.utils;

import com.ky.ulearning.common.core.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author luyuhao
 * @since 2020/4/11 16:48
 */
@Slf4j
public class HttpUtils {

    public static String makeGetQuery(String url, Map<String, String> params) {
        return makeGetQuery(url, params, CommonConstant.CHARSET);
    }

    private static String makeGetQuery(String url, Map<String, String> params, String charset) {
        String query = makeQueryString(params, charset);
        return StringUtils.join(url, "?", query);
    }

    private static String makeQueryString(Map<String, String> params, String charset) {
        if (CollectionUtils.isEmpty(params)) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        boolean hasParam = false;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            if (StringUtil.isEmpty(name)) {
                continue;
            }
            if (hasParam) {
                query.append("&");
            } else {
                hasParam = true;
            }
            query.append(name).append("=").append(EncodeUtil.encodeURL(value));
        }
        return query.toString();
    }
}
