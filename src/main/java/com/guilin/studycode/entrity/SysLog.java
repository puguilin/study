package com.guilin.studycode.entrity;

import java.io.Serializable;
import java.util.Date;

public class SysLog implements Serializable {
    private String id;

    private Integer userId;

    private String userAction;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public SysLog(String id, Integer userId, String userAction, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.userAction = userAction;
        this.createTime = createTime;
    }

    public SysLog() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction == null ? null : userAction.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", userAction=").append(userAction);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}