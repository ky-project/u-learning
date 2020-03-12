package com.ky.ulearning.common.core.utils;

import cn.hutool.core.date.DateTime;
import org.junit.Test;

import java.util.Date;

/**
 * @author luyuhao
 * @since 2020/03/12 23:58
 */
public class DateUtilTest {

    @Test
    public void test01(){
        Date date1 = DateUtil.parseDate("2020-03-12 17:00:05");
        Date date2 = DateUtil.parseDate("2020-03-12 17:50:30");

        long l = DateUtil.betweenMs(date2, date1);
        int remainMin = (int) (l / 1000 / 60);
        System.out.println(l);
        System.out.println(remainMin);
    }

    @Test
    public void test02(){
        DateTime dateTime1 = DateUtil.parse("2020-01-28", "yyyy-MM-dd");
        DateTime dateTime2 = DateUtil.parse("2020-01-23", "yyyy-MM-dd");
        int index = 0;
        DateTime dateTime;
        do{
            dateTime = DateUtil.offsetDay(dateTime1, -(index++));
            System.out.println(DateUtil.format(dateTime, "yyyy-MM-dd"));
        }while (! DateUtil.isSameDay(dateTime, dateTime2));
    }
}
