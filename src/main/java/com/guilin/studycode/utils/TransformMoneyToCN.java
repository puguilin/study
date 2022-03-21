package com.guilin.studycode.utils;

public class TransformMoneyToCN {

    private static final String[] HanDigiStr = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    private static final String[] HanDiviStr = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾",
            "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };

    public static String NumToRMBStr(double paramDouble) throws Exception {

        String str1 = "";
        String str2 = "";
        if (paramDouble < 0.0D) {
            paramDouble = -paramDouble;
            str1 = "负";
        }
        if ((paramDouble > 100000000000000.0D) || (paramDouble < -100000000000000.0D))
            throw new Exception(" 数值位数过大!");
        long l1 = Math.round(paramDouble * 100.0D);
        long l2 = l1 / 100L;
        long l3 = l1 % 100L;
        int i = (int) l3 / 10;
        int j = (int) l3 % 10;
        if ((i == 0) && (j == 0)) {
            str2 = "整";
        } else {
            if ((i != 0) && (String.valueOf(l2).endsWith("0")))
                str2 = "零" + HanDigiStr[i];
            else
                str2 = HanDigiStr[i];
            if ((i != 0) && (j == 0))
                str2 = str2 + "角整";
            if ((i != 0) && (j != 0))
                str2 = str2 + "角";
            if ((l2 == 0L) && (i == 0))
                str2 = "";
            if (j != 0)
                str2 = str2 + HanDigiStr[j] + "分";
        }
        return str1 + PositiveIntegerToHanStr(String.valueOf(l2)) + "元" + str2;
    }

    public static String PositiveIntegerToHanStr(String paramString) {
        String str = "";
        int i = 0;
        int j = 0;
        int k = paramString.length();
        if (k > 15)
            return "数值过大!";
        for (int m = k - 1; m >= 0; m--)
            if (paramString.charAt(k - m - 1) != ' ') {
                int n = paramString.charAt(k - m - 1) - '0';
                if ((n < 0) || (n > 9))
                    return "输入含非数字字符!";
                if (n != 0) {
                    if (i != 0)
                        str = str + HanDigiStr[0];
                    str = str + HanDigiStr[n];
                    str = str + HanDiviStr[m];
                    j = 1;
                } else if ((m % 8 == 0) || ((m % 8 == 4) && (j != 0))) {
                    str = str + HanDiviStr[m];
                }
                if (m % 8 == 0)
                    j = 0;
                i = (n == 0) && (m % 4 != 0) ? 1 : 0;
            }
        if (str.length() == 0)
            return HanDigiStr[0];
        return str;
    }

    public static void main(String[] args) throws Exception {
        //  System.out.println(PositiveIntegerToHanStr("643545"));
        System.out.println(NumToRMBStr(60005.03623));
    }


       private static String getCN(int n) {
           switch (n) {
               case 1:
                   return "壹";
               case 2:
                   return "贰";
               case 3:
                   return "叁";
               case 4:
                   return "肆";
               case 5:
                   return "伍";
               case 6:
                   return "陆";
               case 7:
                   return "柒";
               case 8:
                   return "捌";
               case 9:
                   return "玖";
               case 0:
                   return "零";
           }
           return null;
       }
}
