package com.guilin.studycode.mapper;

import com.guilin.studycode.entrity.BaseInfo;

public interface BaseInfoMapper {
    int insert(BaseInfo record);

    int insertSelective(BaseInfo record);
}