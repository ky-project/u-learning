package com.ky.ulearning.common.core.utils;

import com.ky.ulearning.spi.common.vo.ExaminationParamVo;
import org.junit.Test;

/**
 * @author luyuhao
 * @since 20/03/07 23:03
 */
public class JsonUtilTest {

    @Test
    public void test01(){
        String json = "{\"questionKnowledge\":[{\"lael\":\"测试\"},{\"key\":\"二元一次方程\",\"label\":\"二元一次方程\"}],\"questionDifficulty\":2,\"quantity\":[{\"questionType\":1,\"amount\":5,\"grade\":3},{\"questionType\":2,\"amount\":7,\"grade\":2},{\"questionType\":3,\"amount\":6,\"grade\":5},{\"questionType\":4,\"amount\":10,\"grade\":2}]}";

        ExaminationParamVo examinationParamVo = JsonUtil.parseObject(json, ExaminationParamVo.class);
        System.out.println(examinationParamVo);
    }
}
