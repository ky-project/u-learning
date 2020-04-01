package com.ky.ulearning.monitor.remoting;

import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.spi.common.vo.DruidWebUriVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author luyuhao
 * @since 2020/3/14 16:49
 */
@FeignClient("system-manage")
@RequestMapping(value = "/system-manage")
public interface SystemManageRemoting {

    /**
     * 获取后台管理系统api统计数据
     *
     * @return List<DruidWebUriVo>
     */
    @GetMapping("/druidStat/apiStat")
    JsonResult<List<DruidWebUriVo>> getApiStat();
}
