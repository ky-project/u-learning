package com.ky.ulearning.gateway.utils;

import com.ky.ulearning.common.core.constant.CommonConstant;
import org.junit.Test;

/**
 * @author luyuhao
 * @since 2020/03/28 00:30
 */
public class TempTest {

    @Test
    public void test01(){
        String usernameCom = "16620208" + CommonConstant.COURSE_QUESTION_SEPARATE_JUDGE + 1;

        String username = usernameCom.substring(0, usernameCom.lastIndexOf(CommonConstant.COURSE_QUESTION_SEPARATE_JUDGE));
        String loginType = usernameCom.substring(usernameCom.lastIndexOf(CommonConstant.COURSE_QUESTION_SEPARATE_JUDGE) + CommonConstant.COURSE_QUESTION_SEPARATE_JUDGE.length());

        System.out.println(username);
        System.out.println(loginType);
    }
}
