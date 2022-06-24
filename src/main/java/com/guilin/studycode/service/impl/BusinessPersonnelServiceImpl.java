package com.guilin.studycode.service.impl;

import com.guilin.studycode.entrity.BusinessPersonnel;
import com.guilin.studycode.mapper.BusinessPersonnelMapper;
import com.guilin.studycode.service.BusinessPersonnelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: puguilin
 * @date: 2022/6/24
 * @version: 1.0
 */

@Service
public class BusinessPersonnelServiceImpl  implements BusinessPersonnelService {

    @Resource
    private BusinessPersonnelMapper   businessPersonnelMapper;

    @Override
    public BusinessPersonnel getByPersoneId(Integer personId) {

        return businessPersonnelMapper.selectByPrimaryKey(personId);
    }
}
