package com.guilin.studycode.mapper;

import com.guilin.studycode.entrity.BusinessPersonnel;

public interface BusinessPersonnelMapper {
    int deleteByPrimaryKey(Integer personId);

    int insert(BusinessPersonnel record);

    int insertSelective(BusinessPersonnel record);

    BusinessPersonnel selectByPrimaryKey(Integer personId);

    int updateByPrimaryKeySelective(BusinessPersonnel record);

    int updateByPrimaryKey(BusinessPersonnel record);
}