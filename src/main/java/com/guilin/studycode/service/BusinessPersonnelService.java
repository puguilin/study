package com.guilin.studycode.service;

import com.guilin.studycode.entrity.BusinessPersonnel;

/**
 * @description:
 * @author: puguilin
 * @date: 2022/6/24
 * @version: 1.0
 */

public interface BusinessPersonnelService {

    BusinessPersonnel getByPersoneId(Integer personId);
}
