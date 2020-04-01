package com.ky.ulearning.teacher;

import org.junit.Test;

/**
 * @author luyuhao
 * @since 20/02/04 16:56
 */
public class TestUtil {

    @Test
    public void test01() {
        String s1 = null;
        String s2 = null;
        System.out.println(s1.equals(s2));
    }

    @Test
    public void test02() {
        StringBuffer userIds = new StringBuffer("1,2,3,");
        StringBuffer activityEmail = new StringBuffer("xxx,");
        if (userIds.lastIndexOf(",") != -1
                && userIds.lastIndexOf(",") == userIds.length() - 1) {
            userIds.deleteCharAt(userIds.length() - 1);
        }

        if (activityEmail.lastIndexOf(",") != -1
                && activityEmail.lastIndexOf(",") == activityEmail.length() - 1) {
            activityEmail.deleteCharAt(activityEmail.length() - 1);
        }

        System.out.println(userIds);
        System.out.println(activityEmail);
    }
}
