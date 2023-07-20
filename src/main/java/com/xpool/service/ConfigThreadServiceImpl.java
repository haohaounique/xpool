package com.xpool.service;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xpool.bean.ConfigThread;
import com.xpool.enums.RejectedExecutionHandlerEnum;
import com.xpool.mapper.ConfigThreadMapper;
import com.xpool.utils.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author haohaounique
 * @since 2023-07-20 15:30:55
 */
@Service
@Slf4j
public class ConfigThreadServiceImpl extends ServiceImpl<ConfigThreadMapper, ConfigThread> implements IConfigThreadService, BeanPostProcessor {

    @Autowired
    private SpringBeanUtils springBeanUtils;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public boolean freshAllConfigThreadPool() {
        List<ConfigThread> threads = baseMapper.selectList(new QueryWrapper<ConfigThread>());
        log.info(JSON.toJSONString(threads));
        for (ConfigThread thread : threads) {
            ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) springBeanUtils.getBean(thread.getBeanName());
            executor.setCorePoolSize(thread.getCorePoolSize());
            executor.setMaxPoolSize(thread.getMaxPoolSize());
            executor.setQueueCapacity(thread.getQueueCapacity());
            executor.setKeepAliveSeconds(thread.getKeepAliveSeconds());
            executor.setRejectedExecutionHandler(RejectedExecutionHandlerEnum.getHandlerByName(thread.getRejectedExecutionHandler()));
            executor.setThreadGroup(new ThreadGroup(thread.getThreadGroupName()));
            postProcessAfterInitialization(executor, thread.getBeanName());
        }
        return true;
    }
}