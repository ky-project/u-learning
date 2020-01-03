package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.spi.system.dto.PermissionDto;
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
     * @param permissionDto 待插入的权限数据
     */
    void insert(PermissionDto permissionDto);

    /**
     * 查询所有的权限信息
     *
     * @return 返回权限list
     */
    List<PermissionEntity> getList();

    /**
     * 根据权限码查询权限信息
     *
     * @param permissionSource 权限码
     * @return 返回权限对象
     */
    PermissionEntity getByPermissionSource(String permissionSource);

    /**
     * 根据权限url查询权限信息
     *
     * @param permissionUrl 权限url
     * @return 返回权限对象
     */
    PermissionEntity getByPermissionUrl(String permissionUrl);

    /**
     * 更新有效位
     *
     * @param id       权限id
     * @param valid    有效位的值
     * @param updateBy 更新者
     */
    void updateValidById(@Param("id") Long id, @Param("valid") Integer valid, @Param("updateBy") String updateBy);

    /**
     * 更新权限
     *
     * @param permissionDto 待更新的权限dto
     */
    void update(PermissionDto permissionDto);
}
