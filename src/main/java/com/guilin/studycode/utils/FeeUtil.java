package com.guilin.studycode.utils;

import java.math.BigDecimal;


/**
 * 费用转换
 */
public class FeeUtil {

	/**金额格式 */
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

	//单位：厘转分
	public static String fromLiToFen(String li){
		if(!li.matches(CURRENCY_FEN_REGEX)) {
			li="0";
		}

		String fen ="";
		fen =  BigDecimal.valueOf(Long.valueOf(li)).divide(new BigDecimal(10)).toString();
		return fen;
	}
	
	//单位：分转厘
	public static String fromFenToLi(String fen){
		if(!fen.matches(CURRENCY_FEN_REGEX)) {
			fen="0";
		}
		String li ="";
		li = BigDecimal.valueOf(Long.valueOf(fen)).multiply(new BigDecimal(10)).toString();
		return li;
	}

}

