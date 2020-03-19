package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.utils.ExaminationParamUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.ExaminationResultDto;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.spi.teacher.vo.ExaminationStatusVo;
import com.ky.ulearning.spi.teacher.vo.StatisticalResultsVo;
import com.ky.ulearning.spi.teacher.vo.StudentExaminationStatisticsVo;
import com.ky.ulearning.teacher.dao.ExaminationResultDao;
import com.ky.ulearning.teacher.dao.StudentExaminationTaskDao;
import com.ky.ulearning.teacher.dao.StudentTeachingTaskDao;
import com.ky.ulearning.teacher.service.StudentExaminationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 学生测试service - 实现
 *
 * @author luyuhao
 * @since 2020/03/16 00:48
 */
@Service
@CacheConfig(cacheNames = "studentExaminationTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentExaminationTaskServiceImpl extends BaseService implements StudentExaminationTaskService {

    @Autowired
    private StudentExaminationTaskDao studentExaminationTaskDao;

    @Autowired
    private StudentTeachingTaskDao studentTeachingTaskDao;

    @Autowired
    private ExaminationResultDao examinationResultDao;

    @Override
    public PageBean<StudentExaminationTaskDto> pageList(PageParam pageParam, StudentExaminationTaskDto studentExaminationTaskDto) {
        List<StudentExaminationTaskDto> resultList = studentExaminationTaskDao.listPage(studentExaminationTaskDto, pageParam);

        PageBean<StudentExaminationTaskDto> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(studentExaminationTaskDao.countListPage(studentExaminationTaskDto))
                //设置查询结果
                .setContent(resultList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    public StudentExaminationTaskDto getById(Long id) {
        return studentExaminationTaskDao.getById(id);
    }

    @Override
    public StudentExaminationStatisticsVo getStudentExaminationStatistics(ExaminationTaskEntity examinationTaskEntity) {
        //计算最高分、最低分和平均分
        StudentExaminationStatisticsVo studentExaminationStatisticsVo = studentExaminationTaskDao.getStudentExaminationStatistics(examinationTaskEntity.getId());
        //查询总学生人数
        Integer totalStudent = studentTeachingTaskDao.countByTeachingTaskId(examinationTaskEntity.getTeachingTaskId());
        studentExaminationStatisticsVo.setTotalStudent(totalStudent);
        //获取考试状态
        List<ExaminationStatusVo> examinationStatusList = studentExaminationTaskDao.getExaminationStatus(examinationTaskEntity.getId());
        int count = 0;
        for (ExaminationStatusVo examinationStatus : examinationStatusList) {
            count += examinationStatus.getStuNumber();
        }
        if (examinationStatusList.stream().map(ExaminationStatusVo::getResultType)
                .collect(Collectors.toList()).stream().noneMatch(number -> number.equals(1))) {
            examinationStatusList.add(new ExaminationStatusVo(1, 0));
        }
        if (examinationStatusList.stream().map(ExaminationStatusVo::getResultType)
                .collect(Collectors.toList()).stream().noneMatch(number -> number.equals(2))) {
            examinationStatusList.add(new ExaminationStatusVo(2, 0));
        }
        examinationStatusList.add(new ExaminationStatusVo(0, totalStudent - count));
        studentExaminationStatisticsVo.setExaminationStatusList(examinationStatusList);
        //获取统计结果
        //获取试题参数中各题分值
        Map<Integer, Double> typeGrade = ExaminationParamUtil.getTypeGrade(examinationTaskEntity.getExaminationParameters());
        //查询已完成测试的学生id集合
        List<Long> idList = Optional.ofNullable(studentExaminationTaskDao.getIdByExaminationTaskId(examinationTaskEntity.getId())).orElse(Collections.emptyList());
        for (Long id : idList) {
            double totalScore = 0.0;
            double getScore = 0.0;
            List<ExaminationResultDto> examinationResultDtoList = examinationResultDao.getByExaminingId(id);
            for (ExaminationResultDto examinationResultDto : examinationResultDtoList) {
                totalScore += Optional.ofNullable(typeGrade.get(examinationResultDto.getQuestionType())).orElse(0.0);
                getScore += Optional.ofNullable(examinationResultDto.getStudentScore()).orElse(0.0);
            }
            double percent = getScore / totalScore;
            if (percent >= 0.9) {
                studentExaminationStatisticsVo.addStatistical(4);
            } else if (percent >= 0.7) {
                studentExaminationStatisticsVo.addStatistical(3);
            } else if (percent >= 0.6) {
                studentExaminationStatisticsVo.addStatistical(2);
            } else {
                studentExaminationStatisticsVo.addStatistical(1);
            }
        }

        return studentExaminationStatisticsVo;
    }
}
