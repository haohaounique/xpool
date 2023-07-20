package com.xpool.enums;

import com.xpool.bean.DataPolicy;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * description: 拒绝策略  如有自定义的则实现自定义的拒绝策略
 */
public enum RejectedExecutionHandlerEnum {
    AbortPolicy("abortPolicy", new ThreadPoolExecutor.AbortPolicy()),
    CallerRunsPolicy("callerRunsPolicy", new ThreadPoolExecutor.CallerRunsPolicy()),
    DiscardPolicy("discardPolicy", new ThreadPoolExecutor.DiscardPolicy()),
    DiscardOldestPolicy("discardOldestPolicy", new ThreadPoolExecutor.DiscardOldestPolicy()),
    DataPolicy("dataPolicy", new DataPolicy());

    private String rejectedName;
    private RejectedExecutionHandler handler;

    RejectedExecutionHandlerEnum(String rejectedName, RejectedExecutionHandler handler) {
        this.handler = handler;
        this.rejectedName = rejectedName;
    }

    public String getRejectedName() {
        return rejectedName;
    }

    public RejectedExecutionHandler getHandler() {
        return handler;
    }

    /**
     * 根据拒绝名称获取拒绝策略实现类
     *
     * @param rejectedName
     * @return
     */
    public static RejectedExecutionHandler getHandlerByName(String rejectedName) {
        RejectedExecutionHandlerEnum[] values = RejectedExecutionHandlerEnum.values();
        for (RejectedExecutionHandlerEnum value : values) {
            if (value.getRejectedName().equals(rejectedName)) {
                return value.getHandler();
            }
        }
        //如果没找到，则默认使用拒绝丢弃做法
        return AbortPolicy.handler;
    }

}