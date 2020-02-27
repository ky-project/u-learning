package com.ky.ulearning.monitor.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.TableFileEnum;
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
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class FileRecordServiceImpl extends BaseService implements FileRecordService {

    @Autowired
    private FileRecordDao fileRecordDao;

    @Autowired
    private TeachingTaskNoticeDao teachingTaskNoticeDao;

    @Override
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
    public FileRecordEntity getById(Long id) {
        return fileRecordDao.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String updateBy) {
        fileRecordDao.updateValidById(id, updateBy, 0);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<FileRecordEntity> getAll() {
        return fileRecordDao.getAll();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void scanTeacherTable() {
        fileRecordDao.insertFromTeacher();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void scanStudentTable() {
        fileRecordDao.insertFromStudent();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void scanCourseQuestionTable() {
        fileRecordDao.insertFromCourseQuestion();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void scanTeachingTaskExperimentTable() {
        fileRecordDao.insertFromTeachingTaskExperiment();
    }

    @Override
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
                fileRecordDto.setRecordTable(TableFileEnum.TEACHING_TASK_NOTICE_TABLE.getTableName());
                fileRecordDto.setRecordTableId(teachingTaskNoticeEntity.getId());
                fileRecordDto.setCreateBy("fileRecordHandler");
                fileRecordDto.setUpdateBy("fileRecordHandler");
                fileRecordDao.insert(fileRecordDto);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateTableIdByTableAndUrl(FileRecordEntity fileRecordEntity) {
        TableFileEnum tableFileEnum = TableFileEnum.getByTableName(fileRecordEntity.getRecordTable());
        switch (tableFileEnum) {
            //教师表
            case TEACHER_TABLE:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, TableFileEnum.TEACHER_TABLE.getUrlColumn());
                break;
            //学生表
            case STUDENT_TABLE:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, TableFileEnum.STUDENT_TABLE.getUrlColumn());
                break;
            //试题表
            case COURSE_QUESTION_TABLE:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, TableFileEnum.COURSE_QUESTION_TABLE.getUrlColumn());
                break;
            //实验表
            case TEACHING_TASK_EXPERIMENT_TABLE:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, TableFileEnum.TEACHING_TASK_EXPERIMENT_TABLE.getUrlColumn());
                break;
            //通告表
            case TEACHING_TASK_NOTICE_TABLE:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, TableFileEnum.TEACHING_TASK_NOTICE_TABLE.getUrlColumn());
                break;
            case COURSE_FILE_TABLE:
                fileRecordDao.updateTableIdByTableAndUrl(fileRecordEntity, TableFileEnum.COURSE_FILE_TABLE.getUrlColumn());
                break;
            default:
                break;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateRecordSize(FileRecordEntity fileRecordEntity) {
        fileRecordDao.updateRecordSize(fileRecordEntity);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void scanLogHistoryTable() {
        fileRecordDao.insertFromLogHistory();
    }

    @Override
    public Long getSumFileSize() {
        return fileRecordDao.getSumFileSize();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void scanCourseFileTable() {
        fileRecordDao.insertFromCourseFile();
    }
}
