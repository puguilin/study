package com.guilin.studycode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guilin.studycode.entrity.SysLog;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: puguilin
 * @date: 2022/3/22
 * @version: 1.0
 */
@Service
public interface SysLogService extends IService<SysLog> {

    /**
     * 插入日志
     * @param entity
     * @return
     */
    int insertLog(SysLog entity);
}
