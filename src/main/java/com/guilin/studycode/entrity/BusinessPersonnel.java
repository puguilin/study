package com.guilin.studycode.entrity;

import java.io.Serializable;

public class BusinessPersonnel implements Serializable {
    private Integer personId;

    private String devManCode;

    private String cityCode;

    private String phoneNumber;

    private String name;

    private String certNumber;

    private String roleId;

    private String teamId;

    private String director;

    private String directorPhone;

    private String state;

    private String ifValid;

    private String createTime;

    private String quitTime;

    private String educationId;

    private static final long serialVersionUID = 1L;

    public BusinessPersonnel(Integer personId, String devManCode, String cityCode, String phoneNumber, String name, String certNumber, String roleId, String teamId, String director, String directorPhone, String state, String ifValid, String createTime, String quitTime, String educationId) {
        this.personId = personId;
        this.devManCode = devManCode;
        this.cityCode = cityCode;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.certNumber = certNumber;
        this.roleId = roleId;
        this.teamId = teamId;
        this.director = director;
        this.directorPhone = directorPhone;
        this.state = state;
        this.ifValid = ifValid;
        this.createTime = createTime;
        this.quitTime = quitTime;
        this.educationId = educationId;
    }

    public BusinessPersonnel() {
        super();
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getDevManCode() {
        return devManCode;
    }

    public void setDevManCode(String devManCode) {
        this.devManCode = devManCode == null ? null : devManCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber == null ? null : certNumber.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director == null ? null : director.trim();
    }

    public String getDirectorPhone() {
        return directorPhone;
    }

    public void setDirectorPhone(String directorPhone) {
        this.directorPhone = directorPhone == null ? null : directorPhone.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getIfValid() {
        return ifValid;
    }

    public void setIfValid(String ifValid) {
        this.ifValid = ifValid == null ? null : ifValid.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(String quitTime) {
        this.quitTime = quitTime == null ? null : quitTime.trim();
    }

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId == null ? null : educationId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", personId=").append(personId);
        sb.append(", devManCode=").append(devManCode);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", name=").append(name);
        sb.append(", certNumber=").append(certNumber);
        sb.append(", roleId=").append(roleId);
        sb.append(", teamId=").append(teamId);
        sb.append(", director=").append(director);
        sb.append(", directorPhone=").append(directorPhone);
        sb.append(", state=").append(state);
        sb.append(", ifValid=").append(ifValid);
        sb.append(", createTime=").append(createTime);
        sb.append(", quitTime=").append(quitTime);
        sb.append(", educationId=").append(educationId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}