package com.guilin.studycode.utils;

import cn.hutool.core.date.DateTime;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * string to date 的工具类。 stringToDate、dateToString、两个dateFormat方法，每种格式需要一起加注释
 * 
 * @author wangtongzhen
 */
public class StringDateUtil {
    /**
     * 输出本类的相关log日志
     */
    protected static final Logger logger        = LoggerFactory.getLogger(StringDateUtil.class);

    public static final String    TIME_FORMAT   = "00:00:00";
    public static final String    DAY_ENDTIME   = "23:59";
    public static final String    DAY_BEGINTIME = "00:00";

    private static final ThreadLocal<Map<String, SimpleDateFormat>> sdfMap   = new ThreadLocal<Map<String, SimpleDateFormat>>() {

        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<String, SimpleDateFormat>();
        }
    };

    public static final String                                      PATTERN_YEAR                       = "Y";
    public static final String                                      PATTERN_MONTH                      = "MM";
    public static final String                                      PATTERN_DAY                        = "D";
    public static final String                                      PATTERN_HOUR                       = "H";
    public static final String                                      PATTERN_SECOND                     = "MI";
    public static final String                                      PATTERN_MINUTE                     = "S";


    /**
     * 返回字符串的字节数
     * 
     * @param str
     * @author wangtongzhen
     */
    public static int getStrByteNum(String str) {
        if (str != null) {
            return str.getBytes().length;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        StringDateUtil tuti = new StringDateUtil();
        SimpleDateFormat simpleDateFormat = tuti.dateFormat(4);

        // 格式化字符串  将这种 格式的 时间 Wed Mar 16 14:53:28 CST 2022 格式化为 yyyy-MM-dd HH:mm:ss
        String format = simpleDateFormat.format(new Date());

        //解析为Date   将yyyy-MM-dd HH:mm:ss 解析 Sun Jan 02 23:12:11 CST 2022
        //Date parse = simpleDateFormat.parse("2022-1-2 23:12:11");
        System.out.println("format "+ format);
    }



    /**
     *
     *   字符串类型转换
     *
     *  实例SimpleDateFormat
     *
     *  @param formatNum
     *  1 yyyyMMdd 2 yyyy/MM/dd 3 yyyy-MM-dd 4 yyyy-MM-dd HH:mm:ss
     *  5 yyyyMMddHHmmss 6 yyyy-MM-dd HH:mm:ss.sss 7 HH:mm
     *  8 yyyy年MM月dd日 9 yyyyMMddHHmmssSSS 10 yyyy年MM月dd日 HH:mm
     *  11 yyyy-MM-dd HH 12 HH:mm:ss 17 yyyy年MM月dd日hh时mm分
     *
     *  @return
     *
     *
     */

    private static SimpleDateFormat dateFormat(int formatNum) {
        SimpleDateFormat dateFormatter = null;
        switch (formatNum) {
            case 1:
                dateFormatter = new SimpleDateFormat("yyyyMMdd");
                break;
            case 2:
                dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
                break;
            case 3:
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 4:
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case 5:
                dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
                break;
            case 6:
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
                break;
            case 7:
                dateFormatter = new SimpleDateFormat("HH:mm");
                break;
            case 8:
                dateFormatter = new SimpleDateFormat("yyyy年MM月dd日");
                break;
            case 9:
                dateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                break;
            case 10:
                dateFormatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                break;
            case 11:
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH");
                break;
            case 12:
                dateFormatter = new SimpleDateFormat("HH:mm:ss");
                break;
            case 14:
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+08:00");
                break;
            case 15:
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case 16:
                dateFormatter = new SimpleDateFormat("yyyyMM");
                break;
            case 17:
                dateFormatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
                break;
        }
        return dateFormatter;
    }



    /**
     * 把string类型的日期转化为相应的格式
     * 
     * @param dateString 字符串日期
     * @param formatNum 需要按照哪种类型 1 yyyyMMdd 2 yyyy/MM/dd 3 yyyy-MM-dd 4
     *            yyyy-MM-dd HH:mm:ss 5 yyyyMMddHHmmss 6 yyyy-MM-dd HH:mm:ss.sss
     *            7 HH:mm 8 yyyy年MM月dd日 9 yyyyMMddHHmmssSSS 10 yyyy年MM月dd日 HH:mm
     *            11 yyyy-MM-dd HH 12 HH:mm:ss
     * @return
     */
    public static Date stringToDate(String dateString, int formatNum) {
        Date date = null;
        if (StringUtils.isNotBlank(dateString)) {
            try {
                date = dateFormat(formatNum).parse(dateString);
            } catch (ParseException e) {
                date = null;
                logger.error("StringToDate转换错误", e);
            }
        }

        return date;
    }

    /**
     * 把string类型的日期转化为相应的格式
     * 
     * @param date 字符串日期
     * @param formatNum 1 yyyyMMdd 2 yyyy/MM/dd 3 yyyy-MM-dd 4 yyyy-MM-dd
     *            HH:mm:ss 5 yyyyMMddHHmmss 6 yyyy-MM-dd HH:mm:ss.sss 7 HH:mm 8
     *            yyyy年MM月dd日 9 yyyyMMddHHmmssSSS 10 yyyy年MM月dd日 HH:mm 11
     *            yyyy-MM-dd HH 12 HH:mm:ss 14 yyyy-MM-ddTHH:mm:ss+08:00 15
     *            yyyy-MM-dd HH:mm 16 yyyyMM 17 yyyy年MM月dd日hh时mm分
     * @return
     */
    public static String dateToString(Date date, int formatNum) {
        String str = null;
        if (date != null) {
            str = dateFormat(formatNum).format(date);
        }
        return str;
    }


   /**
    * @param :
    * @return int
    * @author guilin
    * @description 获取当前季度
    * @date 2022/3/18 11:35
    */
    public static int getQuarter() {
        Calendar c = Calendar.getInstance();
        int month = c.get(c.MONTH) + 1;
        int quarter = 0;
        if (month >= 1 && month <= 3) {
            quarter = 1;
        } else if (month >= 4 && month <= 6) {
            quarter = 2;
        } else if (month >= 7 && month <= 9) {
            quarter = 3;
        } else {
            quarter = 4;
        }
        return quarter;
    }


    /**
     * 截取指定字节数的字符串，此方法避免把汉字一分为二
     *
     * @param str
     * @author wangtongzhen
     */
    public static String getCutStrByByte(String str, int chinaSize) {
        int byteSize = chinaSize * 3;//一个汉字等于3个字节
        int byteNum = getStrByteNum(str);

        // 字节数少于指定字节数，直接返回
        if (byteNum <= byteSize) {
            return str;
        }

        int len = str.length();
        String result = "";
        for (int i = chinaSize; i < len; i++) {
            String temp = str.substring(0, i);
            if (getStrByteNum(temp) > byteSize) {
                break;
            } else {
                result = temp;
            }
        }

        return result;
    }


    /**
     * 将String类型转换为Timestamp
     *
     * @param registerTime (String)2010-09-07 23:59:59
     * @return (Timestamp)2010-09-07 23:59:59.0
     */
    public static Timestamp formatDate(String registerTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(registerTime);
        Timestamp ts = Timestamp.valueOf(time);
        return ts;
    }

    /**
     * 功能：当天是当年的第几天
     */
    public static String getDayOfYear() {
        Calendar cal = Calendar.getInstance();
        int dayno = cal.get(Calendar.DAY_OF_YEAR);
        return setMsgContent("000", "" + (dayno - 1));
    }
    private static String setMsgContent(String msg, String content) {
        StringBuffer buffer = new StringBuffer(msg);
        buffer.append(content);
        return buffer.substring(buffer.length() - msg.length(), buffer.length());
    }

    //判断是否包含润年多出的那天
    public static boolean containLeapDay(Date start, Date end) {

        if (isLeapYear(start)) {
            long leapMillions = getLeapDayMillions(start);
            if (leapMillions >= start.getTime() && leapMillions <= end.getTime()) {
                return true;
            }
        }
        if (isLeapYear(end)) {
            long leapMillions = getLeapDayMillions(end);
            if (leapMillions >= start.getTime() && leapMillions <= end.getTime()) {
                return true;
            }
        }
        return false;
    }
    private static long getLeapDayMillions(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 29);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }


    public static boolean isLeapYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 1);

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (maxDay == 29) {
            return true;
        }
        return false;
    }

    /**
     * 功能：计算某一天是当年的第几天 strdate"yyyyMMdd"
     *
     * @throws Exception
     */
    public static String getDayOfYear(String strdate) throws Exception {
        Calendar cal = Calendar.getInstance();
        strdate = strdate.substring(0, 4) + "-" + strdate.substring(4, 6) + "-" + strdate.substring(6, 8);
        try {
            cal.setTime(getDate(strdate));
        } catch (Exception e) {
            throw new Exception();
        }
        int dayno = cal.get(Calendar.DAY_OF_YEAR);
        return setMsgContent("000", "" + (dayno - 1));
    }

    /**
     * 功能：将入参为"yyyy-mm-dd HH:mm:ss"或"yyyy-mm-dd"形式的字符串转换为Date并进行校验；
     */
    public static java.util.Date getDate(String strdate) throws Exception {
        int yyyy = Integer.parseInt(strdate.substring(0, 4));
        String temp = strdate.substring(5, strdate.length());
        int MM = Integer.parseInt(temp.substring(0, temp.indexOf("-")));
        temp = temp.substring(temp.indexOf("-") + 1, temp.length());
        int dd;
        if (temp.indexOf(" ") > 0) {
            dd = Integer.parseInt(temp.substring(0, temp.indexOf(" ")));
        } else {
            dd = Integer.parseInt(temp);
        }
        if (!verityDate(yyyy, MM, dd)) {
            throw new Exception("非法日期数据");
        }

        java.util.Date d;
        try {
            if (strdate.length() > 10) {

                //d = DateFormat.getDateTimeInstance().parse(strdate);
                java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                d = formatter.parse(strdate.substring(0, 19));

            } else {
                java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
                d = formatter.parse(strdate);
            }
            //      System.out.println(formatter.format(d));
            return d;
        } catch (ParseException e) {
            throw new Exception("日期数据转换错" + e.toString());
        }
    }

    /**
     * 根据所给年、月、日，检验是否为合法日期。
     */
    public static boolean verityDate(int yyyy, int MM, int dd) {//
        boolean flag = false;

        if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
            if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
                if (dd <= 30) {
                    flag = true;
                }
            } else if (MM == 2) {
                if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
                    if (dd <= 29) {
                        flag = true;
                    }
                } else if (dd <= 28) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        }
        return flag;
    }

    /**
     *
     * @param al_datetime:
     * @return null
     * @author guilin
     * @description  功能：转换long型为日期型字串并以"yyyy-MM-dd HH:mm:ss"格式返回
     * @date 2022/3/11 9:55
     */

    public static String getDateTime(long al_datetime) {
        java.util.Date date = new java.util.Date(al_datetime);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 功能：把给定日期与给定天数进行加减运算,返回一个新日期
     */
    public static java.util.Date getDateNDays(java.util.Date date, int days) {//
        if (days > 36500 || days < -36500) {
            System.out.println("请把日期限制在100年内");
            return null;
        }
        long l1 = 24, l2 = 60, l3 = 1000, l4 = days;
        long lDays = l1 * l2 * l2 * l3 * l4; //所改变天数的毫秒数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long lCurrentDate = calendar.getTimeInMillis(); //给定日期的毫秒日期
        long lUpdatedDate = lCurrentDate + lDays; //给定日期与给定天数运算后的毫秒日期
        java.util.Date dateNew = new java.util.Date(lUpdatedDate); //结果日期
        return dateNew;
    }

    /**
     *
     * @param days:
     * @return null
     * @author guilin
     * @description 功能：把给定日期与给定天数进行加减运算,返回一个yyyyMMdd的新日期
     * @date 2022/3/11 9:57
     */

    public static String getDateFromNDays(int days) {
        long currentTime = System.currentTimeMillis();
        Date date = getDateNDays(new Date(currentTime), days);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }

    /**
     * 根据所给的起始,终止时间来计算间隔天数
     */
    public static int getIntervalDay(java.sql.Date startDate, java.sql.Date endDate) {//
        long startdate = startDate.getTime();
        long enddate = endDate.getTime();
        long interval = enddate - startdate;
        int intervalday = (int) interval / (1000 * 60 * 60 * 24);
        return intervalday;
    }

    /**
     * 功能：根据所给的起始,终止时间来计算间隔月数；
     *
     * @param as_startDate as_endDate：  yyyymm或yyyy-mm-dd
     */
    public static int getIntervalMonth(String as_startDate, String as_endDate) throws Exception {
        String ls_startD = "", ls_endD = "";
        Date ld_start = null;
        Date ld_end = null;
        if (as_startDate.length() == 6) {//yyyymm型
            ls_startD = as_startDate.substring(0, 4) + "-" + as_startDate.substring(4, as_startDate.length()) + "-01"; //把yyyymm型的年月日期转换为yyyy-mm型的日期,在后面和-01相加,组成一个合法日期
            ls_endD = as_endDate.substring(0, 4) + "-" + as_endDate.substring(4, as_endDate.length()) + "-01"; //把yyyymm型的年月日期转换为yyyy-mm型的日期,在后面和-01相加,组成一个合法日期
        } else {
            ls_startD = as_startDate;
            ls_endD = as_endDate;
        }
        //System.out.println("as_startD:"+as_startD);    //System.out.println("as_endD:"+as_endD);

        try {
            ld_start = getDate(ls_startD);
            ld_end = getDate(ls_endD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        int interval = getIntervalMonth(ld_start, ld_end);
        return interval;
    }

    /**
     * 功能：根据所给的起始,终止时间来计算间隔月数；
     *
     * @param  startDate 入参为Date
     */
    public static int getIntervalMonth(java.util.Date startDate, java.util.Date endDate) {
        java.text.SimpleDateFormat mmformatter = new java.text.SimpleDateFormat("MM");
        int monthstart = Integer.parseInt(mmformatter.format(startDate));
        int monthend = Integer.parseInt(mmformatter.format(endDate));
        java.text.SimpleDateFormat yyyyformatter = new java.text.SimpleDateFormat("yyyy");
        int yearstart = Integer.parseInt(yyyyformatter.format(startDate));
        int yearend = Integer.parseInt(yyyyformatter.format(endDate));
        return (yearend - yearstart) * 12 + (monthend - monthstart);
    }


    /**
     * 获取指定日期的0点0分0秒的时间
     *
     * @param date
     * @return
     */
    public static java.sql.Timestamp getFirstTime(String date) {
        if (date == null || date.equals("")) {
            return null;
        }
        return java.sql.Timestamp.valueOf(date + " 00:00:00.0");
    }

    /**
     * 获取指定日期的23点59分59秒的时间
     *
     * @param date
     * @return
     */
    public static java.sql.Timestamp getLastTime(String date) {
        if (date == null || date.equals("")) {
            return null;
        }
        return java.sql.Timestamp.valueOf(date + " 23:59:59.999");
    }

    /**
     * 获取指定日期的下一个日期
     *
     * @param date
     * @return
     */
    public static Date getNextDate(Date date) {
        if (date == null) {
            return null;
        }
        return new Date(date.getYear(), date.getMonth(), date.getDate() + 1);
    }

    /**
     * 获取当前是一周中的第几天
     *
     * @param date yyyyMMdd 或者 yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static String getDayOfWeek(String date) throws ParseException {
        date = date.replaceAll("[-]", "");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        cal.setTime(sdf.parse(date));
        int week = cal.get(Calendar.DAY_OF_WEEK);
        return String.valueOf((week == 1) ? 7 : week - 1);
    }

    /**
     * 获取本月的最后一天
     *
     * @param date yyyyMMdd 或者 yyyy-MM-dd
     * @return yyyyMMdd
     * @throws ParseException
     */
    public static String getLastDayOfMonth(String date) throws ParseException {
        date = date.replaceAll("[-]", "");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        cal.setTime(sdf.parse(date));
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取下月的最后一天
     *
     * @param date yyyyMMdd 或者 yyyy-MM-dd
     * @return yyyyMMdd
     * @throws ParseException
     */
    public static String getLastDayOfLastMonth(String date) throws ParseException {
        date = date.replaceAll("[-]", "");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        cal.setTime(sdf.parse(date));
        cal.add(Calendar.MONTH, -1);
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    /*
     * 判断传入参数格式为yyyyMMdd的数据是否是有效的日期类型
     */
    public static boolean validDate(String inputDate) {
        return inputDate.matches("[1-9][0-9]{3}(0[0-9]|1[0-2])(0[0-9]|1[0-9]|2[0-9]|3[0-1])");
    }

    /**
     * 获取当前时间的前三个月的时间
     *
     * @return String
     */
    public static String getFirstThreeMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 根据出生日期计算年龄
     *
     * @param birthDay
     * @return
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            logger.warn("The birthDay is after Now. It's unbelievable!");
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        }
        return age;
    }


    /**
     * 判断生日是否在年龄范围内
     *
     * @throws ParseException
     * @param
     * @throws
     */
    public static boolean checkAgeInTheRage(String birthDayStr, int startAge, int endAge) throws ParseException {
        Date birthDay = parseDate(birthDayStr, "yyyy-MM-dd");
        Date endDate = addYears(birthDay, endAge);
        Date startDate = addYears(birthDay, startAge);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        int endDateInt = Integer.parseInt(simpleDateFormat.format(endDate));
        int startDateInt = Integer.parseInt(simpleDateFormat.format(startDate));
        int dateInt = Integer.parseInt(simpleDateFormat.format(new Date()));
        if (dateInt >= startDateInt && dateInt <= endDateInt) {
            return true;
        } else {
            return false;
        }
    }
    public static Date addYears(Date date, int years) {
        if (date == null) {
            return null;
        }
        return new Date(date.getYear() + years, date.getMonth(), date.getDate());
    }

    public static Date parseDate(String date, String pattern) throws ParseException {
        if (sdfMap.get() == null) {
            sdfMap.set(new HashMap<String, SimpleDateFormat>());
        }
        if (sdfMap.get().get(pattern) == null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            sdfMap.get().put(pattern, sdf);
        }
        Date ret = sdfMap.get().get(pattern).parse(date);
        return ret;

    }

    /**
     * @Title: getLastDayOfLastWeek
     * @Description: 获取上周最后一天周日。(西方把周日当做每周的第一天，也就是获取上周的第一天)
     * @param date
     * @return
     * @return:Date
     * @author: pingan.yuan@gmail.com
     * @date: 2015年4月8日 Modification History: Date Author Version Description
     *        ---------------------------------------------------------*
     *        2015年4月8日 pingan.yuan@gmail.com v1.0.0 reason:
     */
    public static Date getLastDayOfLastWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 本周最后一天
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        return calendar.getTime();
    }

    /**** 根据某年的第几周拿到这个周中的某一天并返回 **/
    public static Date getWeekOfDay(int year, int week) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        return cal.getTime();
    }

    /**** 根据某年的第几周拿到这个周的周日的日期 **/
    public static Date getWeekOfDate(int year, int week) {
        return getLastDayOfThisWeek(getWeekOfDay(year, week));
    }

    /*** 得到日期是该年的第几周 ****/
    public static int getWeekNumOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static Date getLastDayOfThisWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return calendar.getTime();
    }



    /**
     * 将datefield取出的String型,直接转换为Timestamp型
     *
     * @param datefield (String)Tue Sep 7 12:00:00 UTC+0800 2010
     * @param pattern (String)yyyy-MM-dd 23:59:59
     * @return (Timestamp)2010-09-07 23:59:59.0
     */
    public static Timestamp formatDatefield(String datefield, String pattern) throws Exception {
        datefield = datefield.replaceAll("UTC ", "UTC+");//url传参时+号会被过滤
        Date date = new Date(datefield);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String time = sdf.format(date);
        Timestamp ts = Timestamp.valueOf(time);
        return ts;
    }

    /**
     * 按指定格式返回时间,实际是舍弃某些格式 （入参是Date对象，出参也是Date对象，比如 date是new Date，则
     * dateFormat(date,1)返回的是时分秒为0的date）
     * 
     * @param date
     * @param formatNum 1 yyyyMMdd 2 yyyy/MM/dd 3 yyyy-MM-dd 4 yyyy-MM-dd
     *            HH:mm:ss 5 yyyyMMddHHmmss 6 yyyy-MM-dd HH:mm:ss.sss 7 HH:mm 8
     *            yyyy年MM月dd日 9 yyyyMMddHHmmssSSS 10 yyyy年MM月dd日 HH:mm 11
     *            yyyy-MM-dd HH 12 HH:mm:ss
     * @return
     */
    public static Date dateFormat(Date date, int formatNum) {
        return stringToDate(dateToString(date, formatNum), formatNum);
    }

    /**
     * 给一个日期加上N天或减去N天得到一个新的日期
     * 
     * @param startDate 需要增加的日期时间
     * @param addnos 添加的天数，可以是正数也可以是负数
     * @return 操作后的日期
     */
    public static Date AddDay(Date startDate, int addnos) {
        if (startDate == null) {
            startDate = new Date();
        }

        Calendar cc = Calendar.getInstance();
        cc.setTime(startDate);
        cc.add(Calendar.DATE, addnos);
        return cc.getTime();
    }

    /**
     * 给一个日期加上N秒或减去N秒得到一个新的日期
     * 
     * @param startDate 需要增加的日期时间
     * @param addnos 添加的秒数，可以是正数也可以是负数
     * @return 操作后的日期
     */
    public static Date AddSecond(Date startDate, int addnos) {
        if (startDate == null) {
            startDate = new Date();
        }

        Calendar cc = Calendar.getInstance();
        cc.setTime(startDate);
        cc.add(Calendar.SECOND, addnos);
        return cc.getTime();
    }

    /**
     * 给一个日期加上N小时或减去N小时得到一个新的日期
     * 
     * @param baseTime 需要增加的日期时间
     * @param amountToAdd 添加的小时数，可以是正数也可以是负数
     * @return 操作后的日期
     */
    public static Date addHours(Date baseTime, int amountToAdd) {
        if (baseTime == null) {
            baseTime = new Date();
        }
        Calendar cc = Calendar.getInstance();
        cc.setTime(baseTime);
        cc.add(Calendar.HOUR, amountToAdd);
        return cc.getTime();
    }

    /**
     * 给一个字符格式的日期加上N天或减去N天得到一个新的日期
     * 
     * @param stringDate 需要增加的日期时间
     * @param addnos 添加的天数，可以是正数也可以是负数
     * @return 操作后的日期
     */
    public static String AddDay(String stringDate, int addnos) {
        int f = 3;
        if (stringDate.indexOf(":") != -1) {
            f = 4;
        }
        Date date = AddDay(stringToDate(stringDate.trim(), f), addnos);
        return dateToString(date, f);
    }

    /**
     * 给一个时间加上N分种或减去N分种后得到一个新的日期
     * 
     * @param startDate 需要增加的日期时间
     * @param addnos 添加的分钟数，可以是正数也可以是负数
     * @return 操作后的日期
     */
    public static Date AddMinute(Date startDate, int addnos) {
        Calendar cc = Calendar.getInstance();
        if (startDate != null) {
            cc.setTime(startDate);
            cc.add(Calendar.MINUTE, addnos);
            return cc.getTime();
        } else {
            return null;
        }
    }

    /**
     * 添加月份
     * 
     * @param startDate
     * @param addnos
     * @return
     */
    public static Date addMonth(Date startDate, int addnos) {
        Calendar cc = Calendar.getInstance();
        if (startDate != null) {
            cc.setTime(startDate);
            cc.add(Calendar.MONTH, addnos);
            return cc.getTime();
        } else {
            return null;
        }
    }

    /**
     * 给一个时间加上N年或减去N年后得到一个新的日期
     * 
     * @param startDate 需要增加的日期时间
     * @param addnos 添加的年数，可以是正数也可以是负数
     * @return 操作后的日期
     */
    public static Date AddYear(Date startDate, int addnos) {
        if (startDate == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int currenYear = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, currenYear + addnos);

        return calendar.getTime();
    }

    /**
     * 得到当前年份
     * 
     * @return
     */
    public static int getNowYear() {
        Calendar cc = Calendar.getInstance();
        return cc.get(Calendar.YEAR);
    }

    /**
     * 得到当前月份
     * 
     * @return
     */
    public static int getNowMonth() {
        Calendar cc = Calendar.getInstance();
        return cc.get(Calendar.MONTH) + 1;
    }

    /**
     * 比较两个日期的相差毫秒数,如果开始日期比结束日期早，则返回正数，否则返回负数。
     * 
     * @param start 开始日期
     * @param end 结束日期
     * @return
     */
    public static long compareDate(Date start, Date end) {
        long temp = 0;
        Calendar starts = Calendar.getInstance();
        Calendar ends = Calendar.getInstance();
        starts.setTime(start);
        ends.setTime(end);
        temp = ends.getTime().getTime() - starts.getTime().getTime();
        return temp;
    }

    /**
     * 比较两个日期的相差天数,如果开始日期比结束日期早，则返回正数，否则返回负数。
     * 
     * @param start
     * @param end
     * @return
     */
    public static long compareDay(Date start, Date end) {
        long day = compareDate(start, end);
        return day / 1000 / 60 / 60 / 24;
    }

    /**
     * 比较两个日期的相差天数,如果开始日期比结束日期早，则返回正数，否则返回负数。 0天返回0、小于等于一天返回1、大于一天返回2、依次类推
     * 
     * @param start
     * @param end
     * @return
     */
    public static long dayDiff(Date start, Date end) {
        long day = compareDate(start, end);
        if (day == 0) {
            return 0;
        }
        if (day > 0) {
            return (day - 1) / 1000 / 60 / 60 / 24 + 1;
        } else {
            return (day + 1) / 1000 / 60 / 60 / 24 - 1;
        }

    }

    /**
     * 得到一个日期的星期几数，星期日返回1以此类推,与真实的要减去1
     * 
     * @param date
     * @return
     */
    public static int getDateIsWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(Calendar.DAY_OF_WEEK);
        return week;
    }

    /**
     * 日期增加减几天 days:天数,可以为负
     * 
     * @param startDate
     * @param days
     * @return
     */
    public static Date addDates(Date startDate, int days) {
        Calendar cc = Calendar.getInstance();
        if (startDate != null) {
            cc.setTime(startDate);
            cc.add(Calendar.DATE, days);
            return cc.getTime();
        } else {
            return null;
        }
    }

    /**
     * 返回一个日期的星期字符串
     * 
     * @param date
     * @return
     */
    public static String getDateToWeek(Date date) {
        int w = getDateIsWeek(date);
        String week = "星期";
        switch (w) {
            case 1:
                week += "日";
                break;
            case 2:
                week += "一";
                break;
            case 3:
                week += "二";
                break;
            case 4:
                week += "三";
                break;
            case 5:
                week += "四";
                break;
            case 6:
                week += "五";
                break;
            case 7:
                week += "六";
                break;
        }
        return week;
    }

    /**
     * 根据日期得到入司时长<br>
     * 1-3月=1;3-6月=3;6-12月=6;12-24月=12;24月以上=24;
     * 请注意，如果一个员工是2008-01-01进入公司的，那么在2008
     * -04-01那天仍然算是(1-3月),2008-04-02就算是(3-6月)的.
     * 
     * @param startWorkTime 入职时间
     * @return 1,3,6,12,24
     */
    public static int getJoinTime(Date startWorkTime) {
        if (startWorkTime == null) {
            return 1;
        }
        Date nowDate = dateFormat(AddDay(new Date(), 1), 3);
        Date newdate = addMonth(startWorkTime, 3);
        long time = compareDate(newdate, nowDate);
        if (time > 0) {
            // 超过3个月
            newdate = addMonth(startWorkTime, 6);
            time = compareDate(newdate, nowDate);
            if (time > 0) {
                newdate = addMonth(startWorkTime, 12);
                time = compareDate(newdate, nowDate);
                if (time > 0) {
                    newdate = addMonth(startWorkTime, 24);
                    time = compareDate(newdate, nowDate);
                    if (time > 0) {
                        return 24;
                    } else {
                        return 12;
                    }
                } else {
                    return 6;
                }
            } else {
                return 3;
            }
        } else {
            return 1;
        }
    }

    /**
     * 提供精确的加法运算。
     * 
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double doubleAdd(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return (b1.add(b2)).doubleValue();
    }

    /**
     * 将DOUBLE类型的数据进行格式化
     * 
     * @param str double类型数据
     * @param max 保存小数点后最多位数据
     * @param min 如果小数点后都为0，则保留最小位数
     * @return
     */
    public static String NumberFormat(double str, int max, int min) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(max);
        nf.setMinimumFractionDigits(min);
        return nf.format(str);
    }

    /**
     * 将string类型转化为double类型,默认为0
     * 
     * @param doublestr
     */
    public static double stringTodouble(String doublestr) {
        double d = 0d;
        if (doublestr == null || doublestr.length() == 0) {
            return d;
        }
        try {
            d = Double.parseDouble(doublestr);
        } catch (Exception e) {
            return d;
        }
        return d;
    }

    /**
     * 将日期格式转化为java.sql.Date类型，如果入参为null，则返回null
     * 
     * @param date
     * @return
     */
    public static java.sql.Date toSqlDate(Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        } else {
            return null;
        }
    }

    /**
     * 将传入的s数转化为时分秒格式
     * 
     * @param time
     * @return
     */
    public static String convertTime(Long time) {
        long timelong = time.longValue();
        if (timelong == 0) {
            return TIME_FORMAT;
        }
        if (timelong > 0) {
            long h = 0, hh = 0;
            long m = 0, mm = 0;
            long s = 0;
            h = timelong / 3600;
            hh = timelong % 3600;
            m = hh / 60;
            mm = hh % 60;
            s = mm;
            String hStr, mStr, sStr = "";
            if (h < 10) {
                hStr = "0" + h;
            } else {
                hStr = "" + h;
            }
            if (m < 10) {
                mStr = "0" + m;
            } else {
                mStr = "" + m;
            }
            if (s < 10) {
                sStr = "0" + s;
            } else {
                sStr = "" + s;
            }
            // if (h > 0) {
            // return hStr + ":" + mStr + ":" + sStr;
            // }
            // if (h == 0 && m > 0) {
            // return mStr + ":" + s;
            // }
            // if (m == 0 && s > 0) {
            // return s + "s";
            // }
            return hStr + ":" + mStr + ":" + sStr;
        }
        return TIME_FORMAT;
    }

    /**
     * 判断字符串是否是Double
     * 
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?\\d*(\\.\\d*)?");
        return pattern.matcher(str).matches();
    }

    /**
     * 根据分割符，把字符串转化集合Set
     * 
     * @param str
     * @param split
     * @return
     */
    public static Set strToTreeSet(String str, String split) {
        Set set = new HashSet();
        if (str != null) {
            set = new HashSet(Arrays.asList(str.split(split)));
        }
        return set;
    }

    /**
     * 取得某一时间的某一个时刻的时间
     * 
     * @param date 某一时间
     * @param millisecond 毫秒数
     */
    public static String getNeedDate(Date date, long millisecond) {
        Date needDate = new Date();
        needDate.setTime(date.getTime() + millisecond);

        return dateFormat(4).format(needDate);
    }

    /**
     * 返回接口需要的格式时间
     * 
     * @param date
     * @return
     */
    public static String getInterFaceTime(Date date) {
        if (date != null) {
            String dateStr = dateToString(date, 4);
            return dateStr.substring(0, 10) + "T" + dateStr.substring(11);
        } else {
            return null;
        }
    }

    /**
     * 接口时间String 转 本地日期Date
     * 
     * @param dateStr
     * @return
     */
    public static Date getInterFaceToLocalTime(String dateStr) {
        if (dateStr != null) {
            dateStr = dateStr.replaceAll("T", " ");
            Date date = StringDateUtil.stringToDate(dateStr, 4);
            return date;
        } else {
            return null;
        }
    }

    /**
     * 判断字符串是否是HH:MM的格式
     * 
     * @param str
     * @return
     */
    public static boolean isTime(String str) {
        Pattern pattern = Pattern.compile("^[0-9]{2}:[0-9]{2}");
        return pattern.matcher(str).matches();
    }

    public static Date getDateTime(String beginTime, String endTime, Date date) {

        String dateString = StringDateUtil.dateToString(date, 3);
        String fromTime = StringDateUtil.dateToString(date, 7);
        if (beginTime == null || endTime == null) {
            return date;
        }
        //检验入参的格式 是不是 HH:MM
        if (!StringDateUtil.isTime(beginTime) || !StringDateUtil.isTime(endTime)) {
            return date;

        }

        if (beginTime.compareTo(endTime) >= 0) {//跨天
            if (fromTime.compareTo(endTime) <= 0) {//当前时间小于等于结束时间，说明开始时间是前一天的
                dateString = dateString + " " + beginTime + ":00";
                return StringDateUtil.AddDay(StringDateUtil.stringToDate(dateString, 4), -1);

            } else if (fromTime.compareTo(beginTime) > 0) {
                dateString = dateString + " " + beginTime + ":00";
                return StringDateUtil.stringToDate(dateString, 4);
            } else {
                return date;
            }

        } else {//不跨天，返回当天日期加beginTime
            dateString = dateString + " " + beginTime + ":00";
            return StringDateUtil.stringToDate(dateString, 4);
        }

    }

    /**
     * 根据指定时间（通常为当前时间）比较开始时间和结束时间来决定短信发送时间 此处的比较只精确到分钟，秒忽略不计
     * 
     * @param beginTime
     * @param endTime
     * @param date
     * @return
     */
    public static Date getCompareTimeResult(String beginTime, String endTime, Date date) {
        if (null != beginTime && beginTime.equals(endTime)) {// 24小时情况
            return date;
        }
        Date returnDate;
        String formTime = StringDateUtil.dateToString(date, 7);
        if (beginTime.compareTo(endTime) > 0) {// 这个条件说明时间段存在跨天
            // 注意:formDate 没有"24:00"的情况
            if (null == formTime) {
                return null;
            }
            if ((beginTime.compareTo(formTime) <= 0 && formTime.compareTo(DAY_ENDTIME) <= 0)
                    || (DAY_BEGINTIME.compareTo(formTime) <= 0 && formTime.compareTo(endTime) <= 0)) {
                returnDate = date;
            } else {// 非跨天段，当天发送
                // (DAY_BEGINTIME.compareTo(formDate)<=0&&beginTime.compareTo(formDate)<=0)
                returnDate = concatDate(date, beginTime);
            }
        } else {
            // 正常的非跨天情况
            if (beginTime.compareTo(formTime) <= 0 && formTime.compareTo(endTime) <= 0) {
                returnDate = date;
            } else {// 跨天段，判断是取当天还是次日
                if (DAY_BEGINTIME.compareTo(formTime) <= 0 && formTime.compareTo(endTime) < 0) {// 0点以后的，在开始时间前的，取当天日期
                    returnDate = concatDate(date, beginTime);
                } else {
                    returnDate = concatDate(AddDay(date, 1), beginTime);
                }
            }
        }
        return returnDate;
    }

    /**
     * 根据当前日期与指定时间，拼接成yyyy-mm-dd HH:mm:ss格式的新的日期
     * 
     * @param date
     * @param formTime
     * @return
     */
    public static Date concatDate(Date date, String formTime) {
        StringBuffer buffer = new StringBuffer();
        String formDay = StringDateUtil.dateToString(date, 3);
        buffer.append(formDay);
        buffer.append(" ");
        if (null == date) {
            return null;
        }
        String[] timeArray = new String[3];
        if (formTime != null && !"".equals(formTime)) {
            String formTimeArray[] = formTime.split(":");
            int flag = 0;
            for (int i = 0; i < formTimeArray.length; i++) {
                if (i == 3) {
                    break;
                }
                timeArray[i] = formTimeArray[i];
                flag++;
            }
            for (int j = flag; j < 3; j++) {
                timeArray[j] = "00";
            }
        } else {
            for (int i = 0; i < 3; i++) {
                timeArray[i] = "00";
            }
        }
        for (int i = 0; i < timeArray.length; i++) {
            buffer.append(timeArray[i]);
            if (i == 2) {
                break;
            }
            buffer.append(":");
        }
        return stringToDate(buffer.toString(), 4);
    }

    /**
     * 根据开始日期(YYYY-MM-DD)，结束日期(YYYY-MM-DD)，
     * 开始时间(HH24:MM)，结束时间(HH24:MM)，判断当前时刻是否有效，注意跨天
     * 
     * @param startDate 开始日期(YYYY-MM-DD)
     * @param endDate 结束日期(YYYY-MM-DD)
     * @param beginTime 开始时间(HH24:MM)
     * @param closeTime 结束时间(HH24:MM)
     * @param needDate 需要判断日期(YYYY-MM-DD HH24:MM:SS)
     * @return boolean
     * @author wangtongzhen
     * @date 2011-9-21
     */
    public static boolean isValidDate(Date startDate, Date endDate, String beginTime, String closeTime, Date needDate) {
        if (null == startDate || null == endDate || StringUtils.isBlank(beginTime) || StringUtils.isBlank(closeTime)
                || null == needDate) {
            return false;
        } else {
            Date bt = stringToDate(beginTime, 7);
            Date et = stringToDate(closeTime, 7);
            Long days = StringDateUtil.dayDiff(startDate, endDate) + 1;//判断开始日期  <= 结束日期
            if (days > 0) {
                List<ForValidDate> lstFvd = new ArrayList<ForValidDate>();
                if (bt.before(et)) {//不跨天
                    for (int i = 0; i < days; i++) {
                        ForValidDate vd = new ForValidDate();
                        vd.setBeginDate(concatDate(AddDay(startDate, i), beginTime));
                        vd.setEndDate(concatDate(AddDay(startDate, i), closeTime));
                        lstFvd.add(vd);
                    }
                } else {
                    for (int i = 0; i < days; i++) {
                        ForValidDate vd = new ForValidDate();
                        vd.setBeginDate(concatDate(AddDay(startDate, i), beginTime));
                        vd.setEndDate(concatDate(AddDay(startDate, i + 1), closeTime));
                        lstFvd.add(vd);
                    }
                }
                if (lstFvd.isEmpty()) {
                    return false;
                } else {
                    boolean flag = false;
                    for (ForValidDate forValidDate : lstFvd) {
                        if (!needDate.before(forValidDate.getBeginDate()) && !needDate.after(forValidDate.getEndDate())) {
                            flag = true;
                            break;
                        }
                    }
                    return flag;
                }
            } else {
                return false;
            }
        }
    }

    static class ForValidDate {
        Date beginDate;
        Date endDate;

        public Date getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(Date beginDate) {
            this.beginDate = beginDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }

    /**
     * 回访根据投保日期和投保人出生年月来判断投保人的年龄 投保人出生：1989-10，如果投保日期是2011-09，则投保人投保年龄为21岁；
     * 如果投保日期为2011-10，则投保人投保年龄为22岁 同一年怎么显示，如果超过月份就显示1，没超过就显示0
     * 一般不会出现投保日期的年月比投保人出生年月早的情况。
     * 
     * @param policyAppdate 投保日期
     * @param holderYearMonth 投保人出生
     * @return age
     */
    public static String reAge(Date policyAppdate, String holderYearMonth) {
        String age = null;
        int ageTemp = 0;
        String date1 = dateToString(policyAppdate, 3);
        String year1 = date1.split("-")[0];
        String month1 = date1.split("-")[1];
        String year2 = holderYearMonth.split("-")[0];
        String month2 = holderYearMonth.split("-")[1];
        int i = Integer.parseInt(month1);
        int j = Integer.parseInt(month2);
        int m = Integer.parseInt(year1);
        int n = Integer.parseInt(year2);
        ageTemp = m - n;
        if (i >= j) {
            if (ageTemp > 0) {
                age = String.valueOf(ageTemp);
            } else {
                age = "0";
            }
        } else {
            if (ageTemp > 0) {
                age = String.valueOf(ageTemp - 1);
            } else {
                age = "0";
            }
        }
        return age;
    }

    /**
     * 计算年龄
     * 
     * @param birthday 出生日期 yyyy-mm-dd
     * @return
     */
    public static int calculateAge(String birthday) {
        String date = dateToString(new Date(), 1);
        Long nowDate = Long.parseLong(date);
        Long birth = Long.parseLong(birthday.replace("-", ""));
        String str = String.valueOf(nowDate - birth);
        int age = Integer.parseInt(str.substring(0, str.length() - 4));
        if (age < 0) {
            logger.warn("出生日期错误！");
        }
        return age;
    }

    /**
     * 如果有空值，返回-1
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static double calculateYear(Date startDate, Date endDate) {
        double difference = -1;
        if (startDate != null && endDate != null) {
            double time = endDate.getTime() - startDate.getTime();
            double year = StringDateUtil.stringToDate("2014-01-21", 3).getTime()
                    - StringDateUtil.stringToDate("2013-01-22", 3).getTime();
            difference = ((int) Math.round((time * 100) / year)) / 100.00;
        }
        return difference;
    }

    /**
     * 计算两个日期相差几天
     * 
     * @param startTime
     * @param endTime
     * @param format 如：yyyy-MM-dd
     * @return
     */
    public static long dateDiff(String startTime, String endTime, String format) {
        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff;
        try {
            //获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            long day = diff / nd;//计算差多少天
            long hour = diff % nd / nh;//计算差多少小时
            long min = diff % nd % nh / nm;//计算差多少分钟
            long sec = diff % nd % nh % nm / ns;//计算差多少秒
            //输出结果
            return day;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据当前日期随机获取字符串（抬头+8位当前日期+num位随机字符） 目前申请保单挂起批单号采用此方法生成
     * 
     * @param headStr 随机字符串抬头字符，默认以P17抬头
     * @return
     */
    public static String getRandomStrNo(String headStr, int num) {
        StringBuilder result = new StringBuilder();
        if (StringUtils.isNotBlank(headStr)) {
            result.append(headStr);
        } else {
            result.append("P17"); // 3位抬头
        }
        result.append(new SimpleDateFormat("yyMMddHH").format(new Date()));// 8位时间
        Random random = new Random();
        String randomStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // 默认4位随机数
        if (num < 4) {
            num = 4;
        }
        // 加1的目的是为了下面randomNum-1不存在负数,同时randomNum能取值为randomStr的length
        int randomNum = random.nextInt(randomStr.length()) + 1;
        for (int i = 0; i < num; i++) {
            result.append(randomStr.subSequence((randomNum - 1), randomNum));
            randomNum = random.nextInt(randomStr.length()) + 1;
        }

        return result.toString();
    }

    /**
     * 根据出生日期和生效日期计算年龄
     * 
     * @param birthDay 出生日期
     * @param effectiveDate 生效日期
     * @return
     */
    public static int getAge(Date birthDay, Date effectiveDate) {
        if (effectiveDate.before(birthDay)) {
            //log.warn("The birthDay is after effectiveDate. It's unbelievable!");
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(effectiveDate);
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        }
        return age;
    }
    
    public static String timeToDateString(String time,int num){
        long lt = new Long(time);
        Date date = new Date(lt);
        return dateFormat(num).format(date);
    }

}
