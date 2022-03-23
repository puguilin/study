package com.guilin.studycode.controller.enums;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.guilin.studycode.utils.CompanyInfoEnum;

public class CompanyInfoEnumController {


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        CompanyInfoEnum enumByValue = CompanyInfoEnum.getEnumByValue(2);
        String fullName = enumByValue.getFullName();
        int lifeCompanyId = enumByValue.getLifeCompanyId();
        System.out.println("enumByValue=="+ enumByValue);
        System.out.println("fullName=="+ fullName);
        System.out.println("lifeCompanyId=="+ lifeCompanyId);
        CompanyInfoEnum[] companys = CompanyInfoEnum.values();
        JSONArray jsonArray = new JSONArray();
        for (CompanyInfoEnum companyInfoEnum : companys) {
            JSONObject obj = new JSONObject();
            obj.put("id", companyInfoEnum.getValue());
            obj.put("name", companyInfoEnum.getText());
            jsonArray.add(obj);
        }
        System.out.println("jsonArray = "+ jsonArray);

    }


}
