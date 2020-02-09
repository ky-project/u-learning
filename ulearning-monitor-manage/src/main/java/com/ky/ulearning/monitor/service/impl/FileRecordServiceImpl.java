package com.ky.ulearning.monitor.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.FileUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.monitor.dao.FileRecordDao;
import com.ky.ulearning.monitor.dao.TeachingTaskNoticeDao;
import com.ky.ulearning.monitor.service.FileRecordService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import com.ky.ulearning.spi.monitor.entity.FileRecordEntity;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author luyuhao
 * @since 20/02/06 17:05
 */
@Service
@CacheConfig(cacheNames = "fileRecord")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class FileRecordServiceImpl extends BaseService implements FileRecordService {

    private static final String TEACHER_TABLE_FILE_COL = "tea_photo";
    private static final String STUDENT_TABLE_FILE_COL = "stu_photo";
    private static final String COURSE_QUESTION_TABLE_FILE_COL = "question_URL";
    private static final String TEACHING_TASK_EXPERIMENT_TABLE_FILE_COL = "experiment_attachment";
    private static final String TEACHING_TASK_NOTICE_TABLE_FILE_COL = "notice_attachment";

    @Autowired
    private FileRecordDao fileRecordDao;

    @Autowired
    private TeachingTaskNoticeDao teachingTaskNoticeDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void insert(FileRecordDto fileRecordDto) {
        fileRecordDao.insert(fileRecordDto);
    }

    @Override
    public PageBean<FileRecordEntity> pageFileRecordList(FileRecordDto fileRecordDto, PageParam pageParam) {
        List<FileRecordEntity> logList = fileRecordDao.listPage(fileRecordDto, pageParam);

        PageBean<FileRecordEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(fileRecordDao.countListPage(fileRecordDto))
                //设置查询结果
                .setContent(logList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public FileRecordEntity getById(Long id) {
        return fileRecordDao.getById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String updateBy) {
        fileRecordDao.updateValidById(id, updateBy, 0);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public List<FileRecordEntity> getAll() {
        return fileRecordDao.getAll();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void scanTeacherTable() {
        fileRecordDao.insertFromTeacher();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void scanStudentTable() {
        fileRecordDao.insertFromStudent();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void scanCourseQuestionTable() {
        fileRecordDao.insertFromCourseQuestion();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void scanTeachingTaskExperimentTable() {
        fileRecordDao.insertFromTeachingTaskExperiment();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void scanTeachingTaskNoticeTable() {
        //获取所有含附件的通告集合
        List<TeachingTaskNoticeEntity> noticeList = teachingTaskNoticeDao.getByNoticeAttachment();
        if (CollectionUtils.isEmpty(noticeList)) {
            return;
        }
        //获取现有的所有文件记录url集合
        Set<String> fileRecordUrlSet = Optional.ofNullable(fileRecordDao.getAll())
                .map(fileRecordEntities -> fileRecordEntities.stream().map(FileRecordEntity::getRecordUrl).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        //遍历集合
        for (TeachingTaskNoticeEntity teachingTaskNoticeEntity : noticeList) {
            //获取url和name
            List<String> attachmentList = StringUtil.strToList(teachingTaskNoticeEntity.getNoticeAttachment(), ",");
            List<String> attachmentNameList = StringUtil.strToList(teachingTaskNoticeEntity.getNoticeAttachmentName(), ",");
            //遍历所有附件
            for (int i = 0; i < attachmentList.size(); i++) {
                String attachment = attachmentList.get(i);
                if (fileRecordUrlSet.contains(attachment)) {
                    continue;
                }
                FileRecordDto fileRecordDto = new FileRecordDto();
                fileRecordDto.setRecordUrl(attachment);
                fileRecordDto.setRecordName(attachmentNameList.get(i));
                fileRecordDto.setRecordType(FileUtil.getExtensionName(attachment));
                fileRecordDto.setRecordTable("u_teaching_task_notice");
                fileRecordDto.setRecordTableId(teachingTaskNoticeEntity.getId());
                fileRecordDto.setCreateBy("fileRecordHandler");
                fileRecordDto.setUpdateBy("fileRecordHandler");
                fileRecordDao.insert(fileRecordDto);
            }
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void updateTableIdByTableAndUrl(FileRecordEntity fileRecordEntity) {
        switch (fileRecordEntity.getRecordTable()) {
            //教师表
            case MicroConstant.TEACHER_TABLE_NAME:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, TEACHER_TABLE_FILE_COL);
                break;
            case MicroConstant.STUDENT_TABLE_NAME:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, STUDENT_TABLE_FILE_COL);
                break;
            case MicroConstant.COURSE_QUESTION_TABLE_NAME:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, COURSE_QUESTION_TABLE_FILE_COL);
                break;
            case MicroConstant.TEACHING_TASK_EXPERIMENT_TABLE_NAME:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, TEACHING_TASK_EXPERIMENT_TABLE_FILE_COL);
                break;
            case MicroConstant.TEACHING_TASK_NOTICE_TABLE_NAME:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, TEACHING_TASK_NOTICE_TABLE_FILE_COL);
                break;
            default:
                break;
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void updateRecordSize(FileRecordEntity fileRecordEntity) {
        fileRecordDao.updateRecordSize(fileRecordEntity);
    }
}
