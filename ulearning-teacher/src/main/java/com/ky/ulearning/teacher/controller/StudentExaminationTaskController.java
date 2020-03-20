package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.ExaminationParamVo;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.spi.teacher.vo.CourseQuestionDetailVo;
import com.ky.ulearning.spi.teacher.vo.ExaminationResultDetailVo;
import com.ky.ulearning.spi.teacher.vo.StudentExaminationResultVo;
import com.ky.ulearning.spi.teacher.vo.StudentExaminationStatisticsVo;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.service.ExaminationResultService;
import com.ky.ulearning.teacher.service.ExaminationTaskService;
import com.ky.ulearning.teacher.service.StudentExaminationTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author luyuhao
 * @since 2020/03/16 00:18
 */
@Slf4j
@RestController
@Api(tags = "学生测试管理", description = "学生测试管理接口")
@RequestMapping(value = "/studentExaminationTask")
public class StudentExaminationTaskController extends BaseController {

    @Autowired
    private StudentExaminationTaskService studentExaminationTaskService;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Autowired
    private ExaminationResultService examinationResultService;

    @Autowired
    private ExaminationTaskService examinationTaskService;

    @Log("分页查询学生测试")
    @ApiOperation(value = "分页查询学生测试", notes = "只能查看/操作已选教学任务的数据")
    @ApiOperationSupport(ignoreParameters = {"id", "stuId"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<StudentExaminationTaskDto>>> pageList(PageParam pageParam,
                                                                                    StudentExaminationTaskDto studentExaminationTaskDto) {
        //校验
        ValidateHandler.checkNull(studentExaminationTaskDto.getExaminationTaskId(), TeacherErrorCodeEnum.EXAMINATION_ID_CANNOT_BE_NULL);
        teachingTaskValidUtil.checkExaminationId(studentExaminationTaskDto.getExaminationTaskId(), RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));

        //查询
        PageBean<StudentExaminationTaskDto> pageBean = studentExaminationTaskService.pageList(setPageParam(pageParam), studentExaminationTaskDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("根据学生测试id查询答题情况")
    @ApiOperation(value = "根据学生测试id查询答题情况", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/getExaminationResultByExaminingId")
    public ResponseEntity<JsonResult<ExaminationResultDetailVo>> getExaminationResultByExaminingId(Long examiningId) {
        ValidateHandler.checkNull(examiningId, TeacherErrorCodeEnum.STUDENT_EXAMINATION_TASK_ID_CANNOT_BE_NULL);
        StudentExaminationTaskDto studentExaminationTaskDto = teachingTaskValidUtil.checkExaminingId(examiningId, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        //获取组卷参数
        String examinationParameters = examinationTaskService.getExaminationParameters(studentExaminationTaskDto.getExaminationTaskId());
        ExaminationParamVo examinationParamVo = JsonUtil.parseObject(examinationParameters, ExaminationParamVo.class);

        //根据学生测试id查询学生答题情况
        Map<Integer, List<CourseQuestionDetailVo>> courseQuestionVoMapList = examinationResultService.getCourseQuestionVoByExaminingId(examiningId, examinationParamVo.getQuantity());

        //创建待返回的数据结构
        ExaminationResultDetailVo examinationResultDetailVo = new ExaminationResultDetailVo();
        examinationResultDetailVo.setExaminingRemainTime(studentExaminationTaskDto.getExaminingRemainTime());
        examinationResultDetailVo.setCourseQuestion(courseQuestionVoMapList);
        return ResponseEntityUtil.ok(JsonResult.buildData(examinationResultDetailVo));
    }

    @Log("根据测试任务id查询学生测试统计")
    @ApiOperation(value = "根据测试任务id查询学生测试统计", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/getStudentExaminationStatistics")
    public ResponseEntity<JsonResult<StudentExaminationStatisticsVo>> getStudentExaminationStatistics(Long examinationTaskId) {
        ValidateHandler.checkNull(examinationTaskId, TeacherErrorCodeEnum.EXAMINATION_ID_CANNOT_BE_NULL);
        //权限校验
        ExaminationTaskEntity examinationTaskEntity = teachingTaskValidUtil.checkExaminationId(examinationTaskId, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));

        StudentExaminationStatisticsVo studentExaminationStatistics = studentExaminationTaskService.getStudentExaminationStatistics(examinationTaskEntity);
        return ResponseEntityUtil.ok(JsonResult.buildData(studentExaminationStatistics));
    }

    @Log("分页查询学生测试结果统计信息")
    @ApiOperation(value = "分页查询学生测试结果统计信息", notes = "只能查看/操作已选教学任务的数据")
    @ApiOperationSupport(ignoreParameters = {"stuScore", "ranking", "accuracy"})
    @GetMapping("/pageStudentExaminationResultList")
    public ResponseEntity<JsonResult<PageBean<StudentExaminationResultVo>>> pageStudentExaminationResultList(PageParam pageParam, StudentExaminationResultVo studentExaminationResultVo) {
        ValidateHandler.checkNull(studentExaminationResultVo.getExaminationTaskId(), TeacherErrorCodeEnum.EXAMINATION_ID_CANNOT_BE_NULL);
        //权限校验
        ExaminationTaskEntity examinationTaskEntity = teachingTaskValidUtil.checkExaminationId(studentExaminationResultVo.getExaminationTaskId(), RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));

        PageBean<StudentExaminationResultVo> pageBean = studentExaminationTaskService.pageStudentExaminationResultList(setPageParam(pageParam), studentExaminationResultVo, examinationTaskEntity.getExaminationParameters());
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

}
