package com.ky.ulearning.gateway.remoting;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author luyuhao
 * @date 19/12/17 02:58
 */
@FeignClient("monitor-manage")
@RequestMapping(value = "/monitor-manage/log")
public interface MonitorManageRemoting {

    /**
     * 添加日志
     *
     * @param logEntity 待添加的日志对象
     */
    @Async
    @PostMapping("/add")
    void add(@RequestParam Map<String, Object> logEntity);
}
