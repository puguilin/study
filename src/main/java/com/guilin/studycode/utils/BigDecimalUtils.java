package com.guilin.studycode.utils;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @description:BigDecimal 计算
 * @author: puguilin
 * @date: 2022/3/10
 * @version: 1.0
 */

public class BigDecimalUtils {

    private static final BigDecimal LEVEL_ONE      = new BigDecimal(800);
    private static final BigDecimal LEVEL_TWO      = new BigDecimal(4000);
    private static final BigDecimal LEVEL_THREE    = new BigDecimal(25000);
    private static final BigDecimal LEVEL_FOUR     = new BigDecimal(62500);
    private static final BigDecimal POINT_TWO      = new BigDecimal("0.2");
    private static final BigDecimal POINT_THREE    = new BigDecimal("0.3");
    private static final BigDecimal POINT_FOUR     = new BigDecimal("0.4");
    private static final BigDecimal POINT_EIGHT    = new BigDecimal("0.8");
    private static final BigDecimal TWO_THOUSAND   = new BigDecimal(2000);
    private static final BigDecimal SEVEN_THOUSAND = new BigDecimal(7000);


    public BigDecimal calculateTax(BigDecimal monthAmount) {
        Preconditions.checkArgument(monthAmount != null);

        BigDecimal newTax;
        //
        /**
         *
         * a.compareTo(b) >0
         * 大于0： 表示 a 大于  b
         *
         * a.compareTo(b) <0
         * 小于0： 表示 a 小于 b
         *
         * 等于0： 表示 a 等于 b
         *
         */

        if (monthAmount.compareTo(LEVEL_ONE) < 0) {
            newTax = BigDecimal.ZERO;
        } else if (monthAmount.compareTo(LEVEL_TWO) <= 0) {
            newTax = monthAmount.subtract(LEVEL_ONE).multiply(POINT_TWO);
        } else if (monthAmount.compareTo(LEVEL_THREE) <= 0) {
            newTax = monthAmount.multiply(POINT_EIGHT).multiply(POINT_TWO);
        } else if (monthAmount.compareTo(LEVEL_FOUR) <= 0) {
            newTax = monthAmount.multiply(POINT_EIGHT).multiply(POINT_THREE).subtract(TWO_THOUSAND);
        } else {
            newTax = monthAmount.multiply(POINT_EIGHT).multiply(POINT_FOUR).subtract(SEVEN_THOUSAND);
        }
        newTax = newTax.setScale(2, RoundingMode.HALF_UP);
        return newTax;
    }

    public static void main(String[] args) {
        BigDecimalUtils utils = new BigDecimalUtils();
        BigDecimal bigDecimal = utils.calculateTax(new BigDecimal(700));
        System.out.println("bigDecimal " + bigDecimal);

    }


}
