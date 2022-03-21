
package com.guilin.studycode.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
* @desc: 生成ID工具类
* @author: puguilin
* @createTime:
* @history:
* @version: v1.0
*/
public class IdUtil {
	
	/**
	 * @param :
	 * @return Long
	 * @author guilin
	 * @description 生成16位数字唯一ID
	 * @date 2022/3/11 17:51
	 */
	public static Long createIdByHashCode() {
        int hashCode = UUID.randomUUID().toString().hashCode();
	      if(hashCode<0){
	          hashCode = -hashCode;
	      }
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        String dateStr = sdf.format(new Date());
        String hashCodeStr = String.valueOf(hashCode);
        String headStr = dateStr.substring(0,16-hashCodeStr.length());
        Long hashCodeId = Long.parseLong(headStr+hashCodeStr);
		return hashCodeId;
	}

	/**
	 * @param :
	 * @return String
	 * @author guilin
	 * @description 生成流水号 yyyyMMddHHmmssSSS + 3位随机数
	 * @date 2022/3/11 17:51
	 */
    public static String createIdByDate() {
        // 精确到毫秒
        SimpleDateFormat fmt = new SimpleDateFormat("(yyyyMMddHHmmssSSS)");
        String suffix = fmt.format(new Date());
        suffix = suffix + "-" + Math.round((Math.random() * 100000));
        return suffix;
    }
    
    /**
	 * @param :
	 * @return String
	 * @author guilin
	 * @description 生成uuid 去掉-
	 * @date 2022/3/11 17:52
	 */
    public static String createIdByUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
