package com.guilin.studycode.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


@Data
public class UserDto {
    @TableId(value = "ID" ,type = IdType.AUTO)
    @ApiModelProperty(value = "唯一标识")
    @TableField("ID")
    private int id;
    @ApiModelProperty(value = "用户名称")
    @TableField("name")
    private String name;
    @ApiModelProperty(value = "用户密码")
    @TableField("password")
    private String password;
    @ApiModelProperty(value = "年龄")
    @TableField("age")
    private String age;
    @ApiModelProperty(value = "性别")
    @TableField("sex")
    private String sex;
    @ApiModelProperty(value = "手机号")
    @TableField("mobile")
    private String mobile;
    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;
    @ApiModelProperty(value = "体重")
    @TableField("height")
    private String height;
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //前台向后台传数据格式
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd") //后端返回向前台展示的时间格式 yyyy-MM-dd HH:mm:ss
    @TableField("createTime")
    private String createTime;
}
