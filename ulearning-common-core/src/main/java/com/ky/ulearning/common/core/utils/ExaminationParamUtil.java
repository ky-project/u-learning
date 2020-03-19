package com.ky.ulearning.common.core.utils;

import com.ky.ulearning.spi.common.vo.ExaminationParamVo;
import com.ky.ulearning.spi.common.vo.QuantityVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试组卷参数util
 *
 * @author luyuhao
 * @since 2020/03/20 00:26
 */
public class ExaminationParamUtil {

    /**
     * 将试题参数转为对象
     *
     * @param examinationParameters 试题参数
     * @return 试题参数对象
     */
    public static ExaminationParamVo getExaminationParamVo(String examinationParameters) {
        return JsonUtil.parseObject(examinationParameters, ExaminationParamVo.class);
    }

    /**
     * 获取总分
     *
     * @param examinationParameters 试题参数
     * @return 总分
     */
    public static Double getTotalScore(String examinationParameters) {
        ExaminationParamVo examinationParamVo = getExaminationParamVo(examinationParameters);
        List<QuantityVo> quantity = examinationParamVo.getQuantity();
        Double totalScore = 0.0;
        for (QuantityVo quantityVo : quantity) {
            totalScore += quantityVo.getGrade() * quantityVo.getAmount();
        }
        return totalScore;
    }

    /**
     * 获取各个题型的分值
     *
     * @param examinationParameters 试题参数
     * @return 获取各个题型的分值
     */
    public static Map<Integer, Double> getTypeGrade(String examinationParameters) {
        ExaminationParamVo examinationParamVo = getExaminationParamVo(examinationParameters);
        List<QuantityVo> quantity = examinationParamVo.getQuantity();
        Map<Integer, Double> resMap = new HashMap<>();
        for (QuantityVo quantityVo : quantity) {
            resMap.put(quantityVo.getQuestionType(), quantityVo.getGrade());
        }
        return resMap;
    }
}
