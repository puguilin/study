package com.guilin.studycode.utils.filewiths;

import java.io.Serializable;
import java.util.List;

/**
 * @description:FileParseResult
 * @author: puguilin
 * @date: 2022/3/11
 * @version: 1.0
 */

public class FileParseResult <T> implements Serializable {

    private String errorDesc;
    private List<T> resultList;
    private Boolean success = false;
    private int total;

    public FileParseResult() {
    }

    public String getErrorDesc() {
        return this.errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public List<T> getResultList() {
        return this.resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
