package com.guilin.studycode.generator.pojo;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private Integer id;

    private String sno;

    private String sname;

    private String ssex;

    private  String remark;

    private Date createdate;

    private Date updatedate;

    private static final long serialVersionUID = 1L;

    public Student() {
    }

    public Student(Integer id, String sno, String sname, String ssex, String remark, Date createdate, Date updatedate) {
        this.id = id;
        this.sno = sno;
        this.sname = sname;
        this.ssex = ssex;
        this.remark = remark;
        this.createdate = createdate;
        this.updatedate = updatedate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSsex() {
        return ssex;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", sno='" + sno + '\'' +
                ", sname='" + sname + '\'' +
                ", ssex='" + ssex + '\'' +
                ", remark='" + remark + '\'' +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                '}';
    }
}