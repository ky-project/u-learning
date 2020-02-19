package com.ky.ulearning.system.auth.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.system.dto.PermissionDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;

import java.util.List;
import java.util.Map;

/**
 * 权限表service-接口类
 *
 * @author luyuhao
 * @date 19/12/08 14:21
 */
public interface PermissionService {
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
     * 查询所有权限
     *
     * @return 返回权限信息list
     */
    List<PermissionEntity> getList();


    /**
     * 删除权限
     *
     * @param id       待删除的权限id
     * @param username 更新者
     */
    void delete(Long id, String username);

    /**
     * 更新权限
     *
     * @param permissionDto 待更新的权限dto对象
     */
    void update(PermissionDto permissionDto);

    /**
     * 分页查询权限
     *
     * @param permission 筛选条件
     * @param pageParam  分页条件
     * @return 权限记录分页
     */
    PageBean<PermissionEntity> pagePermissionList(PermissionDto permission, PageParam pageParam);

    /**
     * 查询所有权限组
     *
     * @return 权限组集合
     */
    List<String> getAllGroup();

    /**
     * 查询所有权限url
     *
     * @return 权限url集合
     */
    List<String> getAllUrl();

    /**
     * 分组查询所有权限
     *
     * @return 返回 组名:组权限list
     */
    Map<String, List<PermissionEntity>> groupList();

    /**
     * 查询权限数组vo
     *
     * @return 权限vo集合
     */
    List<KeyLabelVo> getArrayVoList();
}
