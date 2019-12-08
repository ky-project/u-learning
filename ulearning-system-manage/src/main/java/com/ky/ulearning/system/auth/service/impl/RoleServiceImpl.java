package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.system.auth.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色
 *
 * @author luyuhao
 * @date 19/12/08 18:31
 */
@Service
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class RoleServiceImpl implements RoleService {

}
