package com.guilin.studycode.controller.entiry;

import com.guilin.studycode.entrity.BusinessPersonnel;
import com.guilin.studycode.service.BusinessPersonnelService;
import com.guilin.studycode.utils.ObjectEmptyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: puguilin
 * @date: 2022/6/24
 * @version: 1.0
 */

@RequestMapping("/businessPersonnel")
@RestController
@Api(tags = "商企管理人员信息表--常规dao")
public class BusinessPersonnelController {

    private static final Logger logger = LoggerFactory.getLogger(BusinessPersonnelController.class);

    @Autowired
    private BusinessPersonnelService  businessPersonnelService ;


    @GetMapping("/getByPersoneId")
    @ApiOperation(value = "根据id查询商企管理人员信息")
    @ResponseBody
    public BusinessPersonnel getByPersoneId( Integer personId){

        BusinessPersonnel personer= businessPersonnelService.getByPersoneId(personId);

        boolean notEmpty = ObjectEmptyUtil.isNotEmpty(personer);
        if(notEmpty){
            logger.info("查询成功 personerId:{},商企管理人员:{}",personId,personer);
        }
        return  personer;
    }


}
