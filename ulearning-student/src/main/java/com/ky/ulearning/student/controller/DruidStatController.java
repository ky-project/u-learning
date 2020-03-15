package com.ky.ulearning.student.controller;

import com.alibaba.druid.stat.DruidStatService;
import com.ky.ulearning.common.core.constant.CommonConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.spi.common.vo.DruidWebUriVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * druid监控数据处理器
 *
 * @author luyuhao
 * @since 2020/3/14 10:29
 */
@Slf4j
@RestController
@ApiIgnore
@RequestMapping(value = "/druidStat")
public class DruidStatController {

    private DruidStatService druidStatService = DruidStatService.getInstance();

    @GetMapping("/apiStat")
    public ResponseEntity<JsonResult<List<DruidWebUriVo>>> getApiStat() {
        String service = druidStatService.service(CommonConstant.DRUID_STAT_WEB_URI);
        Map res = JsonUtil.parseObject(service, Map.class);
        Object contentJson = res.get("Content");
        List<DruidWebUriVo> druidWebUriVo = JsonUtil.parseArray(contentJson.toString(), DruidWebUriVo.class);
        return ResponseEntityUtil.ok(JsonResult.buildData(druidWebUriVo));
    }
}
