package com.guilin.studycode.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StudentDto {
    @ApiModelProperty(value = "学生号")
    @TableField("SNO")
    private  String  SNO;

    @ApiModelProperty(value = "学生姓名")
    @TableField("SNAME")
    private   String SNAME;

    @ApiModelProperty(value = "学生性别")
    @TableField("SSEX")
    private  String SSEX;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //前台向后台传数据格式
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd") //后端返回向前台展示的时间格式 yyyy-MM-dd HH:mm:ss
    @TableField("updateDate") //   mysql  中的时间类型 DateTime
    private Date updateDate;
/*
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //前台向后台传数据格式
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") //后端返回向前台展示的时间格式 yyyy-MM-dd HH:mm:ss
    @TableField("creatDate")
    private Date createDate;*/
}
