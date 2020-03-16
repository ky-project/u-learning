package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.spi.common.vo.QuantityVo;
import com.ky.ulearning.spi.teacher.vo.CourseQuestionDetailVo;
import com.ky.ulearning.teacher.dao.ExaminationResultDao;
import com.ky.ulearning.teacher.service.ExaminationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author luyuhao
 * @since 2020/03/16 00:54
 */
@Service
@CacheConfig(cacheNames = "examinationResult")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class ExaminationResultServiceImpl implements ExaminationResultService {

    @Autowired
    private ExaminationResultDao examinationResultDao;

    @Override
    public Map<Integer, List<CourseQuestionDetailVo>> getCourseQuestionVoByExaminingId(Long examiningId, List<QuantityVo> quantityVoList) {
        List<CourseQuestionDetailVo> courseQuestionVoList = Optional.ofNullable(examinationResultDao.getCourseQuestionDetailVoByExaminingId(examiningId))
                .orElse(Collections.emptyList());
        Map<Integer, List<CourseQuestionDetailVo>> resMap = new HashMap<>();
        for (QuantityVo quantityVo : quantityVoList) {
            List<CourseQuestionDetailVo> tmpList = resMap.get(quantityVo.getQuestionType());
            if (CollectionUtils.isEmpty(tmpList)) {
                tmpList = new ArrayList<>();
                resMap.put(quantityVo.getQuestionType(), tmpList);
            }
            for (int i = 0; i < courseQuestionVoList.size(); i++) {
                CourseQuestionDetailVo courseQuestionDetailVo = courseQuestionVoList.get(i);
                if (!courseQuestionDetailVo.getQuestionType().equals(quantityVo.getQuestionType())) {
                    continue;
                }
                //设置试题分数
                courseQuestionDetailVo.setGrade(quantityVo.getGrade());
                //加入临时试题集合
                tmpList.add(courseQuestionDetailVo);
                courseQuestionVoList.remove(i--);
            }
        }
        return resMap;
    }
}
