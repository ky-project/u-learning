package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.CommonConstant;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.*;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.vo.ExaminationParamVo;
import com.ky.ulearning.spi.common.vo.QuantityVo;
import com.ky.ulearning.spi.student.dto.ExaminationResultSaveDto;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.student.entity.StudentExaminationTaskEntity;
import com.ky.ulearning.spi.student.vo.CourseQuestionVo;
import com.ky.ulearning.spi.student.vo.ExaminationResultViewVo;
import com.ky.ulearning.spi.student.vo.ExaminationResultVo;
import com.ky.ulearning.spi.student.vo.StudentExaminationTaskBaseInfoVo;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.common.utils.StudentTeachingTaskUtil;
import com.ky.ulearning.student.service.CourseQuestionService;
import com.ky.ulearning.student.service.ExaminationResultService;
import com.ky.ulearning.student.service.StudentExaminationTaskService;
import com.ky.ulearning.student.service.TeachingTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
    private ExaminationResultService examinationResultService;

    @Autowired
    private CourseQuestionService courseQuestionService;

    @Autowired
    private StudentTeachingTaskUtil studentTeachingTaskUtil;

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Log("开始测试")
    @ApiOperation(value = "开始测试", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/startExaminationTask")
    public ResponseEntity<JsonResult<ExaminationResultVo>> startExaminationTask(Long examinationTaskId) {
        ValidateHandler.checkNull(examinationTaskId, StudentErrorCodeEnum.EXAMINATION_ID_CANNOT_BE_NULL);
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        String stuNumber = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);

        ExaminationTaskEntity examinationTaskEntity = studentTeachingTaskUtil.checkExaminationId(examinationTaskId, stuId);
        ValidateHandler.checkParameter(!CommonConstant.EXAMINATION_STATE[2].equals(examinationTaskEntity.getExaminationState()), StudentErrorCodeEnum.EXAMINATION_TASK_ILLEGAL);
        Long courseId = teachingTaskService.getCourseIdById(examinationTaskEntity.getTeachingTaskId());
        //获取组题参数
        ExaminationParamVo examinationParamVo = JsonUtil.parseObject(examinationTaskEntity.getExaminationParameters(), ExaminationParamVo.class);

        //验证学生是否已开始测试
        StudentExaminationTaskEntity studentExaminationTaskEntity = studentExaminationTaskService.getByExaminationTaskIdAndStuId(examinationTaskId, stuId);
        if (StringUtil.isNotEmpty(studentExaminationTaskEntity)) {
            //验证是否还有剩余测试时间
            ValidateHandler.checkParameter(studentExaminationTaskEntity.getExaminingRemainTime() <= 0 || studentExaminationTaskEntity.getExaminingState() == 2, StudentErrorCodeEnum.STUDENT_EXAMINATION_END);
            //已开始测试，从库中查询测试题目并设置每题分数
            Map<Integer, List<CourseQuestionVo>> courseQuestionVoMapList = examinationResultService.getCourseQuestionVoByExaminingId(studentExaminationTaskEntity.getId(), examinationParamVo.getQuantity());
            //更新状态变更时间
            StudentExaminationTaskDto studentExaminationTaskDto = new StudentExaminationTaskDto();
            studentExaminationTaskDto.setId(studentExaminationTaskEntity.getId());
            studentExaminationTaskDto.setUpdateBy("system");
            studentExaminationTaskDto.setExaminingStateSwitchTime(new Date());
            studentExaminationTaskService.update(studentExaminationTaskDto);
            //创建待返回的数据结构
            ExaminationResultVo examinationResultVo = new ExaminationResultVo();
            examinationResultVo.setExaminingRemainTime(studentExaminationTaskEntity.getExaminingRemainTime());
            examinationResultVo.setCourseQuestion(courseQuestionVoMapList);
            return ResponseEntityUtil.ok(JsonResult.buildData(examinationResultVo));
        }
        //第一次进入测试
        //获取学生ip
        String ip = RequestHolderUtil.getHeaderByName(MicroConstant.USER_REQUEST_IP);
        String cityInfo = IpUtil.getCityInfo(ip);
        ip = cityInfo + "(" + ip + ")";
        StudentExaminationTaskDto studentExaminationTaskDto = initInsertDto(examinationTaskId, stuId, examinationTaskEntity.getExaminationDuration(), stuNumber, ip);
        studentExaminationTaskService.add(studentExaminationTaskDto);
        //开始组题
        Map<Integer, List<CourseQuestionVo>> resMap = randomTestPaper(examinationParamVo, courseId);
        //将组卷结果添加到测试结果表中进行保存
        examinationResultService.batchInsert(resMap, studentExaminationTaskDto.getId());

        //创建待返回的数据结构
        ExaminationResultVo examinationResultVo = new ExaminationResultVo();
        examinationResultVo.setExaminingRemainTime(examinationTaskEntity.getExaminationDuration());
        examinationResultVo.setCourseQuestion(resMap);
        return ResponseEntityUtil.ok(JsonResult.buildData(examinationResultVo));
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

    @Log("保存/提交测试结果")
    @ApiOperation(value = "保存/提交测试结果", notes = "只能查看/操作已选教学任务的数据【注】该接口只接收json格式参数")
    @PostMapping("/submitResult")
    public ResponseEntity<JsonResult<Boolean>> submitResult(@RequestBody ExaminationResultSaveDto examinationResultSaveDto) {
        ValidateHandler.checkParameter(CollectionUtils.isEmpty(examinationResultSaveDto.getQuestionAnswerDtoList()), StudentErrorCodeEnum.EXAMINATION_RESULT_CANNOT_BE_NULL);
        //测试任务id
        Long examinationTaskId = examinationResultSaveDto.getExaminationTaskId();
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        String stuNumber = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);

        //获取测试任务
        ExaminationTaskEntity examinationTaskEntity = studentTeachingTaskUtil.checkExaminationId(examinationTaskId, stuId);
        ValidateHandler.checkParameter(!CommonConstant.EXAMINATION_STATE[2].equals(examinationTaskEntity.getExaminationState()), StudentErrorCodeEnum.EXAMINATION_TASK_ILLEGAL);

        //查询学生测试信息
        StudentExaminationTaskEntity studentExaminationTaskEntity = studentExaminationTaskService.getByExaminationTaskIdAndStuId(examinationTaskId, stuId);
        ValidateHandler.checkNull(studentExaminationTaskEntity, StudentErrorCodeEnum.STUDENT_EXAMINATION_TASK_ILLEGAL);
        ValidateHandler.checkParameter(studentExaminationTaskEntity.getExaminingRemainTime() <= 0 || studentExaminationTaskEntity.getExaminingState() == 2, StudentErrorCodeEnum.STUDENT_EXAMINATION_END);

        //获取当前ip信息
        String ip = RequestHolderUtil.getHeaderByName(MicroConstant.USER_REQUEST_IP);
        String cityInfo = IpUtil.getCityInfo(ip);
        ip = cityInfo + "(" + ip + ")";

        //更新剩余时间和状态变更时间
        Integer remainTime = studentExaminationTaskEntity.getExaminingRemainTime();
        Date examiningStateSwitchTime = studentExaminationTaskEntity.getExaminingStateSwitchTime();
        int subMin = DateUtil.diffDateMin(examiningStateSwitchTime, new Date());
        int newRemainTime = remainTime - subMin;
        //设置待更新的对象
        StudentExaminationTaskDto studentExaminationTaskDto = new StudentExaminationTaskDto();
        studentExaminationTaskDto.setId(studentExaminationTaskEntity.getId());
        studentExaminationTaskDto.setUpdateBy(stuNumber);
        studentExaminationTaskDto.setExaminingRemainTime(newRemainTime);
        studentExaminationTaskDto.setExaminingStateSwitchTime(new Date());
        studentExaminationTaskDto.setExaminingHostIp(ip);
        studentExaminationTaskDto.setExaminingState(newRemainTime <= 0 || examinationResultSaveDto.getIsSubmit() ? 2 : 1);

        studentExaminationTaskService.update(studentExaminationTaskDto);
        //更新测试结果
        examinationResultSaveDto.setExaminingId(studentExaminationTaskEntity.getId());
        examinationResultService.batchUpdate(examinationResultSaveDto);

        //若结束或交卷，计算成绩
        boolean isOver = studentExaminationTaskDto.getExaminingState() != 1;
        if (isOver || examinationResultSaveDto.getIsSubmit()) {
            examinationResultService.calculationResult(studentExaminationTaskEntity.getId());
        }
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(!isOver, "保存成功"));
    }

    @Log("重新组卷")
    @ApiOperation(value = "重新组卷", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/regroup")
    public ResponseEntity<JsonResult<ExaminationResultVo>> regroup(Long examinationTaskId) {
        ValidateHandler.checkNull(examinationTaskId, StudentErrorCodeEnum.EXAMINATION_ID_CANNOT_BE_NULL);
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);

        ExaminationTaskEntity examinationTaskEntity = studentTeachingTaskUtil.checkExaminationId(examinationTaskId, stuId);
        ValidateHandler.checkParameter(!CommonConstant.EXAMINATION_STATE[2].equals(examinationTaskEntity.getExaminationState()), StudentErrorCodeEnum.EXAMINATION_TASK_ILLEGAL);
        Long courseId = teachingTaskService.getCourseIdById(examinationTaskEntity.getTeachingTaskId());
        //获取组题参数
        ExaminationParamVo examinationParamVo = JsonUtil.parseObject(examinationTaskEntity.getExaminationParameters(), ExaminationParamVo.class);

        //验证学生是否已开始测试
        StudentExaminationTaskEntity studentExaminationTaskEntity = studentExaminationTaskService.getByExaminationTaskIdAndStuId(examinationTaskId, stuId);
        ValidateHandler.checkNull(studentExaminationTaskEntity, StudentErrorCodeEnum.STUDENT_EXAMINATION_TASK_ILLEGAL);
        //验证是否还有剩余测试时间
        ValidateHandler.checkParameter(studentExaminationTaskEntity.getExaminingRemainTime() <= 0 || studentExaminationTaskEntity.getExaminingState() == 2, StudentErrorCodeEnum.STUDENT_EXAMINATION_END);

        //开始组题
        Map<Integer, List<CourseQuestionVo>> resMap = randomTestPaper(examinationParamVo, courseId);
        //将组卷结果添加到测试结果表中进行保存
        examinationResultService.batchInsert(resMap, studentExaminationTaskEntity.getId());
        //创建待返回的数据结构
        ExaminationResultVo examinationResultVo = new ExaminationResultVo();
        examinationResultVo.setExaminingRemainTime(studentExaminationTaskEntity.getExaminingRemainTime());
        examinationResultVo.setCourseQuestion(resMap);
        return ResponseEntityUtil.ok(JsonResult.buildData(examinationResultVo));
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
                    courseQuestionVoList.remove(i--);
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

    @Log("查询测试结果明细")
    @ApiOperation(value = "查询测试结果明细", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/getExaminationDetail")
    public ResponseEntity<JsonResult<ExaminationResultViewVo>> getExaminationDetail(Long examinationTaskId) {
        ValidateHandler.checkNull(examinationTaskId, StudentErrorCodeEnum.EXAMINATION_ID_CANNOT_BE_NULL);
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);

        ExaminationTaskEntity examinationTaskEntity = studentTeachingTaskUtil.checkExaminationId(examinationTaskId, stuId);
        //获取组题参数
        ExaminationParamVo examinationParamVo = JsonUtil.parseObject(examinationTaskEntity.getExaminationParameters(), ExaminationParamVo.class);

        //验证学生是否已完成测试
        StudentExaminationTaskEntity studentExaminationTaskEntity = studentExaminationTaskService.getByExaminationTaskIdAndStuId(examinationTaskId, stuId);
        ValidateHandler.checkParameter(Objects.isNull(studentExaminationTaskEntity) || !studentExaminationTaskEntity.getExaminingState().equals(2), StudentErrorCodeEnum.STUDENT_EXAMINATION_TASK_ILLEGAL);

        //获取学生测试结果
        ExaminationResultViewVo examinationResultViewVo = examinationResultService.getCourseQuestionDetailVoByExaminingId(studentExaminationTaskEntity.getId(), examinationParamVo.getQuantity());
        examinationResultViewVo.setExaminationShowResult(examinationTaskEntity.getExaminationShowResult());
        if (!examinationTaskEntity.getExaminationShowResult()) {
            examinationResultViewVo.setCourseQuestion(null);
        }

        //获取学生测试信息，计算排名和提交人数
        List<StudentExaminationTaskBaseInfoVo> studentExaminationTaskBaseInfoVoList = studentExaminationTaskService.getBaseInfoByExaminationTaskId(examinationTaskId);
        examinationResultViewVo.setSubmitNumber(studentExaminationTaskBaseInfoVoList.size());
        int index = 1;
        for (StudentExaminationTaskBaseInfoVo studentExaminationTaskBaseInfoVo : studentExaminationTaskBaseInfoVoList) {
            if (studentExaminationTaskBaseInfoVo.getStuTotalScore() <= examinationResultViewVo.getStuTotalScore()) {
                break;
            }
            index++;
        }
        examinationResultViewVo.setRanking(index);

        return ResponseEntityUtil.ok(JsonResult.buildData(examinationResultViewVo));
    }
}
