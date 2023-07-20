package com.xpool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xpool.bean.ConfigThread;

public interface IConfigThreadService extends IService<ConfigThread> {

    boolean freshAllConfigThreadPool();
}