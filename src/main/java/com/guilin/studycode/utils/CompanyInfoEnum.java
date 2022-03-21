package com.guilin.studycode.utils;

import cn.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

public enum CompanyInfoEnum {

    /**
     * 众安
     * args
     * 1.供应商ID
     * 2.供应商简称
     * 3.供应商全称
     * 4.logo地址
     * 5.保单报告分享头部显示地址
     * 6.客服电话
     */
    ZHONGAN(1, "众安","众安在线财产保险有限责任公司","https://cdn.iyb.tm/static/res/app/img/3133938387db27082768.png","https://cdn.iyb.tm/static/res/app/img/3170180844e49fe51ba9.png","400-999-9595",4),

    /**
     * 和谐
     */
    HEXIE(2, "和谐健康","和谐健康保险股份有限公司","https://cdn.iyb.tm/static/res/app/img/313394343c1ea2e45e25.png","https://cdn.iyb.tm/static/res/app/img/317017342e1096a9111d.png","95569",2),

    /**
     * 复星联合
     */
    FUXINGLIANHE(3, "复星联合健康保险","复星联合健康保险股份有限公司","https://cdn.iyb.tm/static/res/app/img/31339417a33c7ba5d30d.png","https://cdn.iyb.tm/static/res/app/img/3170170641c53efde32c.png","4006-11-7777",5),

    /**
     * 横琴
     */
    HENG_QIN(4, "横琴健康","横琴人寿保险有限公司","https://cdn.iyb.tm/static/res/app/img/31339449f83ed250b58d.png","https://cdn.iyb.tm/static/res/app/img/3170175053fa0b455ef7.png","400-69-12345",27),

    /**
     * 万朵玫瑰
     */
    ANXIE(5, "万朵玫瑰","万朵玫瑰","","","",-1),

    /**
     * 诺辉健康
     */
    NUOHUI(6, "诺辉健康","浙江诺辉健康科技有限公司","","","400-826-2300",-1),

    /**
     * 安心乐业
     */
    AXLEYE(7, "安心互联网保险","安心财产保险有限责任公司","https://cdn.iyb.tm/static/res/app/img/31339808d0b68bd21b26.png","https://cdn.iyb.tm/static/res/app/img/31701675fa3bdd2a3cbc.png","95303",42),

    /**
     * 中国平安
     */
    PINGAN(8, "中国平安","中国平安保险（集团）股份有限公司","https://cdn.iyb.tm/static/res/app/img/3133947547eb38b84c6c.png","https://cdn.iyb.tm/static/res/app/img/3170178056644c30f588.png","4008-8663-38",9),

    /**
     * 中荷
     */
    ZHONGHE_LIFE(9, "中荷人寿","中荷人寿保险有限公司","https://cdn.iyb.tm/static/res/app/img/313394888f6d00778f1d.png","https://cdn.iyb.tm/static/res/app/img/31701794d45d37d02143.png","4008161688",6),

    /**
     * 上海人寿
     */
    SHANGHAI_LIFE(10, "上海人寿","上海人寿保险股份有限公司","https://cdn.iyb.tm/static/res/app/img/313394641be51d1a3d82.png","https://cdn.iyb.tm/static/res/app/img/31701764e4d5f95ea8fe.png","4009118118",7),

    /**
     * 金汇通航
     */
    JHTH(11, "金汇通航","上海金汇通用航空股份有限公司","","","4000216619",-1),

    /**
     * 百年人寿
     */
    BN_LIFE(12, "百年人寿" ,"百年人寿保险股份有限公司" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/aeonlife_logo.png" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/aeonlife_logo.png","95542",20),

    /**
     * 信泰人寿
     */
    XT_LIFE(13, "信泰人寿" ,"信泰人寿保险股份有限公司" , "" , "","95365",21),

    /**
     * 中信保诚人寿
     */
    ZXCB_LIFE(14, "中信保诚人寿" ,"中信保诚人寿保险有限公司" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/citicpru_logo_369x100.png" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/citicpru_logo_369x100.png","95558",24),

    /**
     * 同方全球人寿
     */
    TFQQ_LIFE(15, "同方全球人寿" ,"同方全球人寿保险有限公司" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/tfaegon_logo.png" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/tfaegon_logo.png","95105768",23),

    /**
     * 信美相互
     */
    PICC(16, "人保" ,"中国人民保险" , "https://static.zhongan.com/website/health/iybApp/upload/supplier/rb.png" , "https://static.zhongan.com/website/health/iybApp/upload/supplier/rb.png","95518",18),

    /**
     * 信美相互
     */
    XINMEI(17, "信美相互" ,"信美人寿相互保险社" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/tmlife_275x100.png" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/tmlife_275x100.png","4001399990",35),

    /**
     * 长城人寿
     */
    GWLIFE(18, "长城人寿","长城人寿保险股份有限公司","","","95576",3),

    /**
     * 国联人寿
     */
    GUOLIAN(19, "国联人寿","国联人寿保险股份有限公司","","","4008-888-000",10),

    /**
     * 工银安盛人寿
     */
    ICBCAXA(20, "工银安盛人寿","工银安盛人寿保险有限公司","","","95359",11),

    /**
     * 富邦人壽
     */
    FUBON(21, "富邦人壽","富邦人壽保險股份有限公司","","","",12),

    /**
     * 富衛人壽
     */
    FWD(22, "富衛人壽","富衛人壽保险股份有限公司","","","",13),

    /**
     * 阳光人寿
     */
    YANGGUANG(23, "阳光人寿" ,"阳光人寿保险股份有限公司" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/yglife_logo.png" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/yglife_logo.png","95510",14),

    /**
     * 君康人寿
     */
    JUNKANG(24, "君康人寿","君康人寿保险股份有限公司","","","400-889-3311",15),

    /**
     * 利安人寿
     */
    LIANLIFE(25, "利安人寿","利安人寿保险股份有限公司","","","4008080080",16),

    /**
     * 新华人寿
     */
    NCI(26, "新华人寿","新华人寿保险股份有限公司","","","95567",17),

    /**
     * 全球人壽
     */
    TGL(27, "全球人壽","全球人寿保险有限公司","","","",19),

    /**
     * 百年人寿
     */
    AEONLIFE(28, "百年人寿","百年人寿保险股份有限公司","https://static.zhongan.com/website/health/iyb/resource/logo/company/aeonlife_logo.png","https://static.zhongan.com/website/health/iyb/resource/logo/company/aeonlife_logo.png","95542",20),

    /**
     * 信泰保险
     */
    SINATAY(29, "信泰保险","信泰人寿保险股份有限公司","","","",21),

    /**
     * 华夏人寿
     */
    HUALIFE(30, "华夏人寿","华夏人寿保险股份有限公司","","","95300",25),

    /**
     * 大地保险
     */
    CONTINENT(31, "大地保险","中国大地财产保险股份有限公司","","","400-921-0-921",28),

    /**
     * 中国人寿
     */
    CHINALIFE(32, "中国人寿","中国人寿保险（集团）公司","","","95519",29),

    /**
     * 太平洋保险
     */
    CPIC(33, "太平洋保险","中国太平洋保险（集团）股份有限公司","","","95500",30),

    /**
     * 信达保险
     */
    CINDA(34, "信达保险","信达财产保险股份有限公司","","","4008 667788",31),

    /**
     * 长安责任保险
     */
    CAPLI(35, "长安责任保险","长安责任保险股份有限公司","","","",32),

    /**
     * 中华联合
     */
    CIC(36, "中华联合","中华联合财产保险股份有限公司","","","95585",33),

    /**
     * 众安生命
     */
    ZALIFE(37, "众安生命","众安（深圳）生命科技有限公司 ","","","400-608-3568",40),

    /**
     * 华贵人寿
     */
    HUAGUI(38, "华贵保险" ,"华贵人寿保险股份有限公司" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/hglife_logo.png" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/hglife_logo.png","400-684-1888",36),

    /**
     * 中宏保险
     */
    ZHONGHONG(39, "中宏保险" ,"中宏人寿保险有限公司" , "https://static.zhongan.com/website/health/iybApp/upload/supplier/zhiro_logo.png" , "https://static.zhongan.com/website/health/iybApp/upload/supplier/zhiro_logo.png","95383",-1),

    /**
     * 海峡保险
     */
    HAIXIA(41, "海峡保险" ,"海峡金桥财产保险股份有限公司" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/haixia_icon.png" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/haixia_logo.png","0591-96331",37),

    /**
     * 昆仑健康
     */
    KUNLUN(42, "昆仑健康" ,"昆仑健康保险股份有限公司" , "https://static.zhongan.com/website/health/iyb/resource/product/ware40/logo.jpg" , "https://static.zhongan.com/website/health/iyb/resource/product/ware40/logo.jpg","400-811-8899",39),

    /**
     * 长生人寿
     */
    CHANGSHENG(43, "长生人寿" ,"长生人寿保险有限公司" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/changsheng.jpg" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/changsheng.jpg","400-820-8599",41),

    /**
     * 招商仁和
     */
    CM_LIFE(44, "招商仁和","招商局仁和人寿保险股份有限公司","","","400-86-95666",43),

    /**
     * 恒安标准人寿
     */
    HENGAN_LIFE(45, "恒安标准人寿","恒安标准人寿保险有限公司","","","400-818-8699",45),

    /**
     * 安联财产保险
     */
    ANLIAN(46, "安联财产保险","安联财产保险(中国)有限公司","","","400-800-2020",66),

    /**
     * 史带财产
     */
    SHIDAI(47, "史带财产" ,"史带财产保险股份有限公司" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/shidai_logo.png" , "https://static.zhongan.com/website/health/iyb/resource/logo/company/shidai_logo.png","4009995507",67),

    /**
     * 复星保德信
     */
    FUXINGBAODEXIN(48, "复星保德信","复星保德信人寿保险有限公司","","","400-821-6808",74),

    /**
     * 瑞华健康
     */
    RUIHUA(49, "瑞华健康","瑞华健康保险股份有限公司","","","400-609-6868",76)
    ;


    private int    value;

    private String text;

    private String logo;

    private String logo_h;

    private String fullName;

    private String csTel;

    private int lifeCompanyId;

    private CompanyInfoEnum(int value, String text, String fullName,String logo, String logo_h, String csTel,int lifeCompanyId) {
        this.value = value;
        this.text = text;
        this.fullName = fullName;
        this.logo = logo;
        this.logo_h = logo_h;
        this.csTel = csTel;
        this.lifeCompanyId = lifeCompanyId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCsTel() {
        return csTel;
    }

    public void setCsTel(String csTel) {
        this.csTel = csTel;
    }

    public String getLogo_h() {
        return logo_h;
    }

    public void setLogo_h(String logo_h) {
        this.logo_h = logo_h;
    }

    public int getLifeCompanyId() {
        return lifeCompanyId;
    }

    public void setLifeCompanyId(int lifeCompanyId) {
        this.lifeCompanyId = lifeCompanyId;
    }

    /**
     * 根据枚举值获取枚举类型
     *
     * @param value
     * @return
     */
    public static CompanyInfoEnum getEnumByValue(int value) {
        CompanyInfoEnum[] ens = CompanyInfoEnum.values();
        for (CompanyInfoEnum en : ens) {
            if (en.getValue() == value) {
                return en;
            }
        }
        return null;
    }

    public static CompanyInfoEnum getEnumByValue(String text) {
        CompanyInfoEnum[] ens = CompanyInfoEnum.values();
        for (CompanyInfoEnum en : ens) {
            if (StringUtils.endsWith(text, en.getText())) {
                return en;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CompanyInfoEnum[] companys = CompanyInfoEnum.values();
        for (int i = 0; i < companys.length; i++) {
            CompanyInfoEnum oneEnum = companys[i];
            JSONObject content = new JSONObject();
            content.put("code", oneEnum.value);
            content.put("key", oneEnum);
            content.put("name", oneEnum.text);
            content.put("fullName", oneEnum.fullName);
            content.put("logo", oneEnum.logo);
            content.put("logoHead", oneEnum.logo_h);
            content.put("csTel", oneEnum.csTel);
            content.put("lifeCompanyId", oneEnum.lifeCompanyId);

            StringBuffer sb = new StringBuffer();
            sb.append("insert into iyb_dict(label,content,type,description,creator,gmt_created,modifier,gmt_modified,is_deleted) VALUES(");
            sb.append("'"+oneEnum.value+"','"+content.toString()+"','company_info_enum','公司/供应商枚举','niuqun',NOW(),'niuqun',NOW(),'N');");
            //System.out.println(oneEnum);
            System.out.println(sb.toString());
        }
    }
}
