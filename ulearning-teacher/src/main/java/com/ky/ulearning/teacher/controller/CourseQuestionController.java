package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.constant.CommonErrorCodeEnum;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.constant.TableFileEnum;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.FileUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.teacher.dto.CourseQuestionDto;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;
import com.ky.ulearning.spi.teacher.dto.QuestionDto;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.remoting.MonitorManageRemoting;
import com.ky.ulearning.teacher.service.CourseQuestionService;
import com.ky.ulearning.teacher.service.TeachingTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 试题controller
 *
 * @author luyuhao
 * @since 20/02/03 19:54
 */
@Slf4j
@RestController
@Api(tags = "试题管理", description = "试题管理接口")
@RequestMapping(value = "/courseQuestion")
public class CourseQuestionController extends BaseController {

    @Autowired
    private CourseQuestionService courseQuestionService;

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Autowired
    private MonitorManageRemoting monitorManageRemoting;

    @Log("添加试题图片")
    @ApiOperation(value = "添加试题图片", notes = "添加图片，返回图片url")
    @PostMapping("/saveQuestionFile")
    public ResponseEntity<JsonResult<Map<String, Object>>> saveAttachments(MultipartFile questionFile) throws IOException {
        ValidatorBuilder.build()
                .on(questionFile == null, CommonErrorCodeEnum.FILE_CANNOT_BE_NULL)
                //文件类型篡改校验
                .on(!FileUtil.fileTypeCheck(questionFile), CommonErrorCodeEnum.FILE_TYPE_TAMPER)
                //文件类型校验
                .on(!FileUtil.fileTypeRuleCheck(questionFile, FileUtil.IMAGE_TYPE), CommonErrorCodeEnum.FILE_TYPE_ERROR)
                //文件大小校验
                .on(questionFile.getSize() > defaultConfigParameters.getPhotoMaxSize(), CommonErrorCodeEnum.FILE_SIZE_ERROR)
                .doValidate().checkResult();
        String questionUrl = fastDfsClientWrapper.uploadFile(questionFile);
        Map<String, Object> map = new HashMap<>(4);
        map.put("questionUrl", questionUrl);

        //记录文件
        monitorManageRemoting.addFileRecord(getFileRecordDto(questionUrl, questionFile,
                TableFileEnum.COURSE_QUESTION_TABLE.getTableName(), null, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class)));
        return ResponseEntityUtil.ok(JsonResult.buildData(map));
    }

    @Log("添加试题")
    @ApiOperation("添加试题")
    @ApiOperationSupport(ignoreParameters = {"id", "courseId"})
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(QuestionDto questionDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(questionDto.getQuestionText()), TeacherErrorCodeEnum.QUESTION_TEXT_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(questionDto.getQuestionKnowledge()), TeacherErrorCodeEnum.QUESTION_KNOWLEDGE_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(questionDto.getQuestionType()), TeacherErrorCodeEnum.QUESTION_TYPE_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(questionDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //教学任务id校验
        teachingTaskValidUtil.checkTeachingTask(username, questionDto.getTeachingTaskId());
        //获取课程教学任务对象并校验
        CourseTeachingTaskDto courseTeachingTaskDto = teachingTaskService.getById(questionDto.getTeachingTaskId());
        ValidateHandler.checkParameter(courseTeachingTaskDto == null, TeacherErrorCodeEnum.TEACHING_TASK_NOT_EXISTS);
        //设置获取到的courseId
        questionDto.setCourseId(courseTeachingTaskDto.getCourseId());
        questionDto.setUpdateBy(username);
        questionDto.setCreateBy(username);
        //保存试题
        courseQuestionService.save(questionDto);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(questionDto.getId(), "添加成功"));
    }

    @Log("分页查询课程试题")
    @ApiOperation(value = "分页查询课程试题", notes = "只能查看自己教学任务的课程试题")
    @ApiOperationSupport(ignoreParameters = {"id", "courseId", "questionUrl", "courseNumber", "courseName", "courseCredit"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<CourseQuestionDto>>> pageList(PageParam pageParam,
                                                                            CourseQuestionDto courseQuestionDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(courseQuestionDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL);
        //教学任务id校验
        teachingTaskValidUtil.checkTeachingTask(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), courseQuestionDto.getTeachingTaskId());
        PageBean<CourseQuestionDto> pageBean = courseQuestionService.pageList(setPageParam(pageParam), courseQuestionDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("根据id查询课程试题")
    @ApiOperation(value = "根据id查询课程试题", notes = "只能查看自己教学任务的课程试题")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<CourseQuestionDto>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        CourseQuestionDto courseQuestionDto = teachingTaskValidUtil.checkCourseQuestionId(id, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildData(courseQuestionDto));
    }

    @Log("更新试题")
    @ApiOperation("更新试题")
    @ApiOperationSupport(ignoreParameters = {"courseId", "teachingTaskId"})
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(QuestionDto questionDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(questionDto.getId()), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //获取原试题对象
        CourseQuestionDto courseQuestionDto = teachingTaskValidUtil.checkCourseQuestionId(questionDto.getId(), username);
        //设置获取到的courseId防止篡改
        questionDto.setCourseId(courseQuestionDto.getCourseId());
        questionDto.setUpdateBy(username);
        questionDto.setCreateBy(username);
        //更新试题
        courseQuestionService.update(questionDto);

        //判断是否需要删除原图片
        if (StringUtil.isNotEmpty(courseQuestionDto.getQuestionUrl())) {
            if (StringUtil.isEmpty(questionDto.getQuestionUrl())
                    || !courseQuestionDto.getQuestionUrl().equals(questionDto.getQuestionUrl())) {
                fastDfsClientWrapper.deleteFile(courseQuestionDto.getQuestionUrl());
            }
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("删除试题")
    @ApiOperation(value = "删除试题", notes = "只能操作自己教学任务的课程试题")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        CourseQuestionDto courseQuestionDto = teachingTaskValidUtil.checkCourseQuestionId(id, username);
        courseQuestionService.delete(id, username);

        //判断是否需要删除原图片
        if (StringUtil.isNotEmpty(courseQuestionDto.getQuestionUrl())) {
            fastDfsClientWrapper.deleteFile(courseQuestionDto.getQuestionUrl());
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("查询所有知识模块")
    @ApiOperation(value = "查询所有知识模块")
    @GetMapping("/getAllKnowledge")
    public ResponseEntity<JsonResult<List<KeyLabelVo>>> getAllKnowledge() {
        List<KeyLabelVo> keyLabelVoList = courseQuestionService.getAllKnowledge();
        return ResponseEntityUtil.ok(JsonResult.buildData(keyLabelVoList));
    }
}
