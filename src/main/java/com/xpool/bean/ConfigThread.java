package com.xpool.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author haohaounique
 * @since 2023-07-20 15:30:55
 */
@Getter
@Setter
@TableName("config_thread")
public class ConfigThread implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * bean名称
     */
    private String beanName;

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 核心线程数
     */
    private Integer maxPoolSize;

    /**
     * 空闲存活时间
     */
    private Integer keepAliveSeconds;

    /**
     * 队列大小
     */
    private Integer queueCapacity;

    /**
     * 拒绝策略
     */
    private String rejectedExecutionHandler;

    /**
     * 线程组名称
     */
    private String threadGroupName;

    /**
     * json描述,扩展使用
     */
    private String vJson;


}