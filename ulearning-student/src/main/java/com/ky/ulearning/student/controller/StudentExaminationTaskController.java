package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.IpUtil;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.vo.CourseQuestionVo;
import com.ky.ulearning.spi.common.vo.ExaminationParamVo;
import com.ky.ulearning.spi.common.vo.QuantityVo;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.common.utils.StudentTeachingTaskUtil;
import com.ky.ulearning.student.service.CourseQuestionService;
import com.ky.ulearning.student.service.StudentExaminationTaskService;
import com.ky.ulearning.student.service.TeachingTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author luyuhao
 * @since 2020/03/08 00:55
 */
@Slf4j
@RestController
@Api(tags = "学生测试管理", description = "学生测试管理接口")
@RequestMapping(value = "/studentExaminationTask")
public class StudentExaminationTaskController extends BaseController {

    @Autowired
    private StudentExaminationTaskService studentExaminationTaskService;

    @Autowired
    private CourseQuestionService courseQuestionService;

    @Autowired
    private StudentTeachingTaskUtil studentTeachingTaskUtil;

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Log("开始测试")
    @ApiOperation(value = "开始测试", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/startExaminationTask")
    public ResponseEntity<JsonResult<Map<Integer, List<CourseQuestionVo>>>> startExaminationTask(Long examinationTaskId) {
        ValidateHandler.checkNull(examinationTaskId, StudentErrorCodeEnum.EXAMINATION_ID_CANNOT_BE_NULL);
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        String stuNumber = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);

        ExaminationTaskEntity examinationTaskEntity = studentTeachingTaskUtil.checkExaminationId(examinationTaskId, stuId);
        Long courseId = teachingTaskService.getCourseIdById(examinationTaskEntity.getTeachingTaskId());
        //TODO 验证学生是否已开始测试
        //第一次进入测试
        //获取学生ip
        String ip = RequestHolderUtil.getHeaderByName(MicroConstant.USER_REQUEST_IP);
        String cityInfo = IpUtil.getCityInfo(ip);
        ip = cityInfo + "(" + ip + ")";
        StudentExaminationTaskDto studentExaminationTaskDto = initInsertDto(examinationTaskId, stuId, examinationTaskEntity.getExaminationDuration(), stuNumber, ip);
        //TODO 测试完成打开
//        studentExaminationTaskService.add(studentExaminationTaskDto);
        //开始组题
        ExaminationParamVo examinationParamVo = JsonUtil.parseObject(examinationTaskEntity.getExaminationParameters(), ExaminationParamVo.class);
        //TODO 将组卷结果添加到测试结果表中进行保存
        return ResponseEntityUtil.ok(JsonResult.buildData(randomTestPaper(examinationParamVo, courseId)));
    }

    /**
     * 初始化添加的学生测试对象
     */
    private StudentExaminationTaskDto initInsertDto(Long examinationTaskId, Long stuId, Integer examiningRemainTime, String stuNumber, String ip) {
        StudentExaminationTaskDto studentExaminationTaskDto = new StudentExaminationTaskDto();
        studentExaminationTaskDto.setExaminationTaskId(examinationTaskId);
        studentExaminationTaskDto.setStuId(stuId);
        studentExaminationTaskDto.setExaminingRemainTime(examiningRemainTime);
        studentExaminationTaskDto.setCreateBy(stuNumber);
        studentExaminationTaskDto.setUpdateBy(stuNumber);
        studentExaminationTaskDto.setExaminingHostIp(ip);
        return studentExaminationTaskDto;
    }

    /**
     * 随机组卷
     *
     * @param examinationParamVo 组卷参数
     * @return 试题集合
     */
    private Map<Integer, List<CourseQuestionVo>> randomTestPaper(ExaminationParamVo examinationParamVo, Long courseId) {
        //根据知识点和困难度查询所有试题
        List<CourseQuestionVo> courseQuestionVoList = randomSort(courseQuestionService.getByKnowledgeAndDifficultyAndCourseId(examinationParamVo.getQuestionKnowledges(), examinationParamVo.getQuestionDifficulty(), courseId));
        Map<Integer, List<CourseQuestionVo>> resMap = new HashMap<>();
        List<QuantityVo> quantity = examinationParamVo.getQuantity();
        //遍历题型参数集合
        for (QuantityVo quantityVo : quantity) {
            //提取指定题型集合
            List<CourseQuestionVo> courseQuestionVoTmpList = resMap.get(quantityVo.getQuestionType());
            if (CollectionUtils.isEmpty(courseQuestionVoTmpList)) {
                courseQuestionVoTmpList = new ArrayList<>();
                resMap.put(quantityVo.getQuestionType(), courseQuestionVoTmpList);
            }
            //对试题精选筛选
            for (int i = 0; i < courseQuestionVoList.size(); i++) {
                CourseQuestionVo courseQuestionVo = courseQuestionVoList.get(i);
                if (!courseQuestionVo.getQuestionType().equals(quantityVo.getQuestionType())) {
                    continue;
                }

                if (courseQuestionVoTmpList.size() >= quantityVo.getAmount()) {
                    continue;
                }
                //设置试题分数
                courseQuestionVo.setGrade(quantityVo.getGrade());
                //加入临时试题集合
                courseQuestionVoTmpList.add(courseQuestionVo);
                courseQuestionVoList.remove(i--);
            }
        }
        return resMap;
    }

    /**
     * 设置试题数组的随机值
     *
     * @param courseQuestionVoList 试题数组集合
     * @return 试题数组集合
     */
    private List<CourseQuestionVo> randomSort(List<CourseQuestionVo> courseQuestionVoList) {
        Random random = new Random();
        for (CourseQuestionVo courseQuestionVo : courseQuestionVoList) {
            courseQuestionVo.setRandomOrder(random.nextInt(100));
        }
        courseQuestionVoList.sort(new Comparator<CourseQuestionVo>() {
            @Override
            public int compare(CourseQuestionVo o1, CourseQuestionVo o2) {
                if (o1.getRandomOrder() > o2.getRandomOrder()) {
                    return -1;
                } else if (o1.getRandomOrder() < o2.getRandomOrder()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return courseQuestionVoList;
    }
}
