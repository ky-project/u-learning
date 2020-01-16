package com.ky.ulearning.common.core.utils;

import com.ky.ulearning.spi.common.vo.TermVo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 学期工具类
 *
 * @author luyuhao
 * @since 20/01/17 02:05
 */
public class TermUtil {

    /**
     * 获取前preYears年后nextYears的学期集合
     *
     * @param preYears  前几年
     * @param nextYears 后几年
     * @return 返回学期集合
     */
    public static List<TermVo> getTermList(Integer preYears, Integer nextYears) {
        List<TermVo> termVoList = new ArrayList<>();
        //获得当年年份
        int year = DateUtil.year(new Date());

        int tmpYear = year;
        for (int i = 0; i < preYears + 1; i++, tmpYear--) {
            int leftYear = tmpYear - 1;
            TermVo termVo1 = new TermVo()
                    .setTermItem(String.format("%d-%d-1", leftYear, tmpYear));
            TermVo termVo2 = new TermVo()
                    .setTermItem(String.format("%d-%d-2", leftYear, tmpYear));
            termVoList.add(termVo1);
            termVoList.add(termVo2);
        }

        tmpYear = year + 1;
        for (int i = 0; i < nextYears; i++, tmpYear++) {
            int leftYear = tmpYear - 1;
            TermVo termVo1 = new TermVo()
                    .setTermItem(String.format("%d-%d-1", leftYear, tmpYear));
            TermVo termVo2 = new TermVo()
                    .setTermItem(String.format("%d-%d-2", leftYear, tmpYear));
            termVoList.add(termVo1);
            termVoList.add(termVo2);
        }

        termVoList.sort(Comparator.comparing(TermVo::getTermItem));
        return termVoList;
    }


    public static void main(String[] args) {
        System.out.println(getTermList(3, 3));
    }
}
