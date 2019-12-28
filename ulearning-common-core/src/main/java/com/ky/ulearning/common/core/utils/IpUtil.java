package com.ky.ulearning.common.core.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;

/**
 * IP工具类
 *
 * @author luyuhao
 * @date 19/12/05 02:31
 */
@Slf4j
public class IpUtil {

    private static final String UNKNOWN = "unknown";

    /**
     * 用于IP定位转换
     */
    public static final String REGION = "内网IP|内网IP";

    /**
     * 获取ip地址
     */
    public static String getIP(HttpServletRequest request) {
        if(request == null){
            return UNKNOWN;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] ips = ip.split(",");
        return "0:0:0:0:0:0:0:1".equals(ips[0]) ? "127.0.0.1" : ips[0];
    }

    /**
     * 根据ip获取详细地址
     */
    public static String getCityInfo(String ip) {
        try {
            if(StringUtil.isEmpty(ip)){
                return "";
            }
            String path = "ip2region/ip2region.db";
            String name = "ip2region.db";
            int algorithm = DbSearcher.BTREE_ALGORITHM;
            String absolutePath = new ClassPathResource(path).getAbsolutePath();
            DbConfig config = new DbConfig();
            File file = FileUtil.inputStreamToFile(new ClassPathResource(path).getStream(), name);
            log.info(file.getAbsolutePath());
            DbSearcher searcher = new DbSearcher(config, file.getPath());
            Method method;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
                default:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }
            DataBlock dataBlock = null;
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            String address = dataBlock.getRegion().replace("0|", "");
            if (address.charAt(address.length() - 1) == '|') {
                address = address.substring(0, address.length() - 1);
            }
            return address.equals(REGION) ? "内网IP" : address;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
