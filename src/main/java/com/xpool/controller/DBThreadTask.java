package com.xpool.controller;

import com.xpool.service.IConfigThreadService;
import com.xpool.utils.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class DBThreadTask {


    @Autowired
    private SpringBeanUtils springBeanUtils;

    @Resource(name = "tuTask")
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private IConfigThreadService service;

    @RequestMapping("/getTask")
    public Object getTask() {

        executor.execute(() -> {
            System.out.println("调用成功");
        });
        return "ok";
    }

    @RequestMapping("/updateTask")
    public Object updateTask() {
        service.freshAllConfigThreadPool();
        return "ok";
    }
}