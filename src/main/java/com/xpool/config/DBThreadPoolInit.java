package com.xpool.config;

import com.alibaba.fastjson2.JSON;
import com.xpool.bean.ConfigThread;
import com.xpool.enums.RejectedExecutionHandlerEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 通过加载数据库配置到达动态控制线程池参数的目的
 */
@Slf4j
public class DBThreadPoolInit implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.info("registerBeanDefinitions start .....");
        List<ConfigThread> threads = loadDataFromDB(environment);
        log.info("registerBeanDefinitions init config:{}", JSON.toJSONString(threads));
        registerBean(registry, threads);
        log.info("registerBeanDefinitions end .....");
    }

    private static void registerBean(BeanDefinitionRegistry registry, List<ConfigThread> threads) {
        Map<String, Object> propertyValues = new HashMap<>();
        for (ConfigThread thread : threads) {
            propertyValues.put("corePoolSize", thread.getCorePoolSize());
            propertyValues.put("maxPoolSize", thread.getMaxPoolSize());
            propertyValues.put("keepAliveSeconds", thread.getKeepAliveSeconds());
            propertyValues.put("queueCapacity", thread.getQueueCapacity());
            propertyValues.put("allowCoreThreadTimeOut", false);
            propertyValues.put("rejectedExecutionHandler", RejectedExecutionHandlerEnum.getHandlerByName(thread.getRejectedExecutionHandler()));
            propertyValues.put("threadGroup", new ThreadGroup(thread.getThreadGroupName()));
            propertyValues.put("beanName", thread.getBeanName());
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(ThreadPoolTaskExecutor.class);
            for (Map.Entry<String, Object> entry : propertyValues.entrySet()) {
                beanDefinition.getPropertyValues().addPropertyValue(entry.getKey(), entry.getValue());
            }
            registry.registerBeanDefinition(thread.getBeanName(), beanDefinition);
            propertyValues.clear(); //用完一次清理一次
        }
    }

    public List<ConfigThread> loadDataFromDB(Environment environment) {

        List<ConfigThread> list = new ArrayList<>();
        try {
            //加载mysql驱动
            Class.forName(environment.getProperty("spring.datasource.driver-class-name"));
            //获取数据库连接
            Connection conn = DriverManager.getConnection(environment.getProperty("spring.datasource.url"), environment.getProperty("spring.datasource.username"), environment.getProperty("spring.datasource.password"));
            //编写sql语句
            String sql = "select id,bean_name,core_pool_size,max_pool_size,keep_alive_seconds,queue_capacity,rejected_execution_handler,thread_group_name,v_json from config_thread ";
            //创建Statement
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet r = statement.executeQuery();
            //执行sql语句
            while (r.next()) {
                ConfigThread thread = new ConfigThread();
                thread.setId(r.getInt("id"));
                thread.setBeanName(r.getString("bean_name"));
                thread.setCorePoolSize(r.getInt("core_pool_size"));
                thread.setMaxPoolSize(r.getInt("max_pool_size"));
                thread.setKeepAliveSeconds(r.getInt("keep_alive_seconds"));
                thread.setQueueCapacity(r.getInt("queue_capacity"));
                thread.setRejectedExecutionHandler(r.getString("rejected_execution_handler"));
                thread.setThreadGroupName(r.getString("thread_group_name"));
                thread.setVJson(r.getString("v_json"));
                list.add(thread);
            }
            if (r != null) {
                r.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


}