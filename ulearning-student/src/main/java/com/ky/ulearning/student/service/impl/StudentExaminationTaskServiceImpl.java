package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.ExaminationParamVo;
import com.ky.ulearning.spi.common.vo.QuantityVo;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.student.entity.StudentExaminationTaskEntity;
import com.ky.ulearning.spi.student.vo.CourseQuestionAccuracyVo;
import com.ky.ulearning.spi.student.vo.CourseQuestionViewVo;
import com.ky.ulearning.spi.student.vo.ExaminationResultViewVo;
import com.ky.ulearning.spi.student.vo.StudentExaminationTaskBaseInfoVo;
import com.ky.ulearning.student.dao.ExaminationResultDao;
import com.ky.ulearning.student.dao.StudentExaminationTaskDao;
import com.ky.ulearning.student.service.StudentExaminationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 学生测试service - 实现
 *
 * @author luyuhao
 * @since 2020/03/08 00:53
 */
@Service
@CacheConfig(cacheNames = "studentExaminationTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentExaminationTaskServiceImpl extends BaseService implements StudentExaminationTaskService {

    @Autowired
    private StudentExaminationTaskDao studentExaminationTaskDao;

    @Autowired
    private ExaminationResultDao examinationResultDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void add(StudentExaminationTaskDto studentExaminationTaskDto) {
        studentExaminationTaskDao.insert(studentExaminationTaskDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public StudentExaminationTaskEntity getByExaminationTaskIdAndStuId(Long examinationTaskId, Long stuId) {
        return studentExaminationTaskDao.getByExaminationTaskIdAndStuId(examinationTaskId, stuId);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(StudentExaminationTaskDto studentExaminationTaskDto) {
        studentExaminationTaskDao.update(studentExaminationTaskDto);
    }

    @Override
    public List<StudentExaminationTaskBaseInfoVo> getBaseInfoByExaminationTaskId(Long examinationTaskId) {
        return Optional.ofNullable(studentExaminationTaskDao.getBaseInfoByExaminationTaskId(examinationTaskId))
                .orElse(Collections.emptyList());
    }

    @Override
    public PageBean<ExaminationResultViewVo> pageList(PageParam pageParam, Date submitTime, Long stuId, Long teachingTaskId, String examinationName) {
        //查询所有测试数据
        List<ExaminationResultViewVo> resList = Optional.ofNullable(studentExaminationTaskDao.listPage(pageParam, submitTime, stuId, teachingTaskId, examinationName)).orElse(Collections.emptyList());

        //计算需要的字段
        for (ExaminationResultViewVo examinationResultViewVo : resList) {
            //获取组题参数
            ExaminationParamVo examinationParamVo = JsonUtil.parseObject(examinationResultViewVo.getExaminationParameters(), ExaminationParamVo.class);
            //查询测试结果
            List<CourseQuestionViewVo> courseQuestionVoList = Optional.ofNullable(examinationResultDao.getCourseQuestionDetailVoByExaminingId(examinationResultViewVo.getId())).orElse(Collections.emptyList());
            //遍历获取基本数据
            double totalScore = 0.0;
            double stuTotalScore = 0.0;
            Map<Integer, List<CourseQuestionViewVo>> courseQuestionMap = new HashMap<>();
            Map<Integer, CourseQuestionAccuracyVo> accuracyMap = new HashMap<>();
            //遍历组卷参数
            for (QuantityVo quantityVo : examinationParamVo.getQuantity()) {
                //提取准确率和答题详情集合
                List<CourseQuestionViewVo> courseQuestionTmpList = courseQuestionMap.get(quantityVo.getQuestionType());
                //集合初始化
                if (CollectionUtils.isEmpty(courseQuestionTmpList)) {
                    courseQuestionTmpList = new ArrayList<>();
                    courseQuestionMap.put(quantityVo.getQuestionType(), courseQuestionTmpList);
                }
                //创建统计准确率对象
                CourseQuestionAccuracyVo courseQuestionAccuracyVo = new CourseQuestionAccuracyVo();
                //遍历所有答题结果，计算数据
                for (int i = 0; i < courseQuestionVoList.size(); i++) {
                    //获取对象
                    CourseQuestionViewVo courseQuestionDetailVo = courseQuestionVoList.get(i);
                    //判断是否是当前的试题类型
                    if (!courseQuestionDetailVo.getQuestionType().equals(quantityVo.getQuestionType())) {
                        continue;
                    }
                    //设置试题分数
                    courseQuestionDetailVo.setGrade(quantityVo.getGrade());
                    totalScore += quantityVo.getGrade();
                    stuTotalScore += Optional.ofNullable(courseQuestionDetailVo.getStudentScore()).orElse(0.0);
                    //统计总题数和正确题数
                    courseQuestionAccuracyVo.setQuestionNumber(courseQuestionAccuracyVo.getQuestionNumber() + 1);
                    courseQuestionAccuracyVo.setCorrectNumber(courseQuestionAccuracyVo.getCorrectNumber() + (courseQuestionDetailVo.getStudentScore() != null && courseQuestionDetailVo.getStudentScore() > 0.0 ? 1 : 0));

                    //加入临时试题集合
                    courseQuestionTmpList.add(courseQuestionDetailVo);
                    courseQuestionVoList.remove(i--);
                }
                accuracyMap.put(quantityVo.getQuestionType(), courseQuestionAccuracyVo);
            }
            examinationResultViewVo.setTotalScore(totalScore);
            examinationResultViewVo.setStuTotalScore(stuTotalScore);
            examinationResultViewVo.setCourseQuestion(examinationResultViewVo.getExaminationShowResult() ? courseQuestionMap : null);
            examinationResultViewVo.setAccuracy(accuracyMap);

            //获取学生测试信息，计算排名和提交人数
            List<StudentExaminationTaskBaseInfoVo> studentExaminationTaskBaseInfoVoList = Optional.ofNullable(studentExaminationTaskDao.getBaseInfoByExaminationTaskId(examinationResultViewVo.getExaminationTaskId())).orElse(Collections.emptyList());
            int index = 1;
            for (StudentExaminationTaskBaseInfoVo studentExaminationTaskBaseInfoVo : studentExaminationTaskBaseInfoVoList) {
                if (studentExaminationTaskBaseInfoVo.getStuTotalScore() <= examinationResultViewVo.getStuTotalScore()) {
                    break;
                }
                index++;
            }
            examinationResultViewVo.setSubmitNumber(studentExaminationTaskBaseInfoVoList.size());
            examinationResultViewVo.setRanking(index);
        }


        PageBean<ExaminationResultViewVo> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(studentExaminationTaskDao.countListPage(submitTime, stuId, teachingTaskId, examinationName))
                //设置查询结果
                .setContent(resList);
        return setPageBeanProperties(pageBean, pageParam);
    }
}
