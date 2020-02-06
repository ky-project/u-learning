package com.ky.ulearning.monitor.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.monitor.dao.FileRecordDao;
import com.ky.ulearning.monitor.service.FileRecordService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import com.ky.ulearning.spi.monitor.entity.FileRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/06 17:05
 */
@Service
@CacheConfig(cacheNames = "fileRecord")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class FileRecordServiceImpl extends BaseService implements FileRecordService {

    @Autowired
    private FileRecordDao fileRecordDao;

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
}
