package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.spi.system.dto.PermissionInsertDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限dao
 *
 * @author luyuhao
 * @date 19/12/08 03:48
 */
@Mapper
@Repository
public interface PermissionDao {

    /**
     * 查询所有权限source
     *
     * @return 返回source集合
     */
    List<String> getSources();

    /**
     * 插入权限记录
     *
     * @param permissionInsertDto 待插入的权限数据
     */
    void insert(PermissionInsertDto permissionInsertDto);

    /**
     * 查询所有的权限信息
     *
     * @return 返回权限list
     */
    List<PermissionEntity> getList();
}
