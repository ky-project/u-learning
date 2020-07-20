package com.ky.ulearning.common.core.utils;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luyuhao
 * @since 2020/3/14 14:03
 */
public class HttpUtilTest {

    private HttpClientUtil httpClientUtil;

    @Before
    public void init() {
        httpClientUtil = new HttpClientUtil();
        httpClientUtil.init();
    }

    @Test
    public void test01() {
        String register = "null";
        String[] split = register.split(",");
        Date startDate = null;
        Date endDate = null;
        for (String s : split) {
            String httpResult = httpClientUtil.getForString("url");
            if (StringUtil.isEmpty(httpResult)) {
                continue;
            }
            Date startDateTmp;
            Date endDateTmp;
            try {
                Map<String, Date> stringDateMap = subDate(httpResult);
                startDateTmp = stringDateMap.get("startDate");
                endDateTmp = stringDateMap.get("endDate");
                if ((startDate == null && endDate == null)
                        || (startDate != null && endDate != null && DateUtil.compare(endDate, endDateTmp) > 0)) {
                    startDate = startDateTmp;
                    endDate = endDateTmp;
                }
            } catch (Exception e) {
                System.out.println("parse error");
            }
        }
        if (startDate == null || endDate == null) {
            System.out.println("get error");
        } else {
            System.out.println("startDate = " + startDate);
            System.out.println("endDate = " + endDate);
        }
    }

    private Map<String, Date> subDate(String httpResult) throws ParseException {
        String startTag = "起始时间:";
        String endTag = "截止时间:";
        int startIndex = httpResult.indexOf(startTag) + startTag.length();
        int endIndex = httpResult.indexOf(startTag) + startTag.length() + 10;
        String startTimerStr = httpResult.substring(startIndex, endIndex);
        startIndex = httpResult.indexOf(endTag) + endTag.length();
        endIndex = httpResult.indexOf(endTag) + endTag.length() + 10;
        String endTimeStr = httpResult.substring(startIndex, endIndex);
        //格式化获取到的起始时间并返回
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Date> resMap = new HashMap<>();
        resMap.put("startDate", df.parse(startTimerStr));
        resMap.put("endDate", df.parse(endTimeStr));
        return resMap;
    }

    @Test
    public void test02(){
        Map<String, String> param = new HashMap<>();
        param.put("code", "xxxx.xx");
        param.put("cycle", "d");
        param.put("edate", "");
        param.put("sdate", "");

        String httpResult = httpClientUtil.getForString("url", param);
        System.out.println(httpResult);
    }

    @Test
    public void test03(){
        String tmp = "1482167.0433";
        System.out.println("接收: " + tmp);
        System.out.println("转float: " + Float.valueOf(tmp));
        System.out.println("转double: " + Double.valueOf(tmp));
    }

    @Test
    public void test04(){
        String code = "023tpXVI1uERo10dz1WI1HhXVI1tpXVT";
        String appid = "wxb248e7e33f255980";
        String secret = "733571c5fcd87c19058506f27bac13e3";
        String url =    "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid +
                "&secret="  + secret +
                "&js_code=" + code   +
                "&grant_type=authorization_code";
        String jsonResult = httpClientUtil.getForString(url);
        System.out.println(jsonResult);
    }
}
