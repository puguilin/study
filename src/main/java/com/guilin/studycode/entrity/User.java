package com.guilin.studycode.entrity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_user")
@TableName(value="tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @TableField("create_Time")
    private Date createTime;
    @ApiModelProperty(value = "备注")
    @TableField(exist = false) // 数据库不存该字段  可以用来响应
    private String remark;



}
