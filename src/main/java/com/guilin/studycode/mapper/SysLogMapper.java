package com.guilin.studycode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guilin.studycode.entrity.SysLog;

public interface SysLogMapper extends BaseMapper<SysLog> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}