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
}
