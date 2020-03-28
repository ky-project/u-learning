package com.ky.ulearning.common.core.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务统一通用常量
 *
 * @author luyuhao
 * @date 19/12/12 23:06
 */
public class MicroConstant {

    /**
     * 系统教师角色名
     */
    public static final String SYS_ROLE_TEACHER = "teacher";

    /**
     * 系统学生角色名
     */
    public static final String SYS_ROLE_STUDENT = "student";

    /**
     * 学生登录类型
     */
    public static final int LOGIN_TYPE_STUDENT = 3;

    /**
     * 教师登录类型
     */
    public static final int LOGIN_TYPE_TEACHER = 2;

    /**
     * 后台登录类型
     */
    public static final int LOGIN_TYPE_ADMIN = 1;

    /**
     * 用户ip
     */
    public static final String USER_REQUEST_IP = "userIp";

    /**
     * 用户账号
     */
    public static final String USERNAME = "username";

    /**
     * 用户id
     */
    public static final String USER_ID = "userId";

    /**
     * 日志类型
     */
    public static final String[] LOG_TYPE = {"INFO", "ERROR"};

    /**
     * 根目录名
     */
    public static final String ROOT_FOLDER = "\\";

    /**
     * 根目录的母节点id
     */
    public static final long ROOT_FOLDER_PARENT_ID = 0L;

    /**
     * 文件夹
     */
    public static final int FOLDER_TYPE = 2;

    /**
     * 文件
     */
    public static final int FILE_TYPE = 1;

    /**
     * 登录用户信息存于redis中的前缀
     */
    public static final String LOGIN_USER_REDIS_PREFIX = "loginUser::";

    /**
     * 登录用户信息存于redis的有效期
     */
    public static final long LOGIN_USER_REDIS_EXPIRE = 7200;

    /**
     * 实验状态 0：未提交 1：已提交 2：已批改
     */
    public static final int[] EXPERIMENT_STATUS = {0, 1, 2};

}
