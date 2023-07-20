package com.xpool.config;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * description: 导入各种自定义加载的类
 */
@Component
@Import(DBThreadPoolInit.class)
public class ConfigImport {
}