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

@Table(name = "student")
@TableName(value="student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "ID" ,type = IdType.AUTO)  数据库中是自增的 在代码层中 可加也可不加 不影响
    @TableId(value = "ID" ,type = IdType.AUTO)
    @ApiModelProperty(value = "唯一标识")
    @TableField("ID")
    private  int ID;

    @ApiModelProperty(value = "学生号")
    @TableField("SNO")
    private  String  SNO;

    @ApiModelProperty(value = "学生姓名")
    @TableField("SNAME")
    private   String SNAME;

    @ApiModelProperty(value = "学生性别")
    @TableField("SSEX")
    private  String SSEX;

    /****备注 exist = false 表示不是数据库中的字段 推荐使用 ****/
    @TableField(exist = false)
    @ApiModelProperty(value = "备注")
    private  String remark;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //前台向后台传数据格式
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd") //后端返回向前台展示的时间格式 yyyy-MM-dd HH:mm:ss
    @TableField("updateDate") //   mysql  中的时间类型 DateTime
    private Date updateDate;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //前台向后台传数据格式
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd") //后端返回向前台展示的时间格式 yyyy-MM-dd HH:mm:ss
    @TableField("createDate") //   mysql  中的时间类型 DateTime
    private Date createDate;


}
