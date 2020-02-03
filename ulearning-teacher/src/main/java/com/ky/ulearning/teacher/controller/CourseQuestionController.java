package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.constant.CommonErrorCodeEnum;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.FileUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.teacher.dto.CourseQuestionDto;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;
import com.ky.ulearning.spi.teacher.vo.NoticeAttachmentVo;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.service.CourseQuestionService;
import com.ky.ulearning.teacher.service.TeachingTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
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
        return ResponseEntityUtil.ok(JsonResult.buildData(map));
    }

    @Log("添加试题")
    @ApiOperation("添加试题")
    @ApiOperationSupport(ignoreParameters = {"id", "courseId"})
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(CourseQuestionDto courseQuestionDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(courseQuestionDto.getQuestionText()), TeacherErrorCodeEnum.QUESTION_TEXT_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(courseQuestionDto.getQuestionKnowledge()), TeacherErrorCodeEnum.QUESTION_KNOWLEDGE_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(courseQuestionDto.getQuestionType()), TeacherErrorCodeEnum.QUESTION_TYPE_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(courseQuestionDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //教学任务id校验
        teachingTaskValidUtil.checkTeachingTask(username, courseQuestionDto.getTeachingTaskId());
        //获取课程教学任务对象并校验
        CourseTeachingTaskDto courseTeachingTaskDto = teachingTaskService.getById(courseQuestionDto.getTeachingTaskId());
        ValidateHandler.checkParameter(courseTeachingTaskDto == null, TeacherErrorCodeEnum.TEACHING_TASK_NOT_EXISTS);
        //设置获取到的courseId
        courseQuestionDto.setCourseId(courseTeachingTaskDto.getCourseId());
        courseQuestionDto.setUpdateBy(username);
        courseQuestionDto.setCreateBy(username);
        //保存试题
        courseQuestionService.save(courseQuestionDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
    }
}
