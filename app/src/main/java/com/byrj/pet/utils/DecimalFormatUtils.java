package com.byrj.pet.utils;

import java.text.DecimalFormat;

/**
 * double保留两位小数
 *
 * @author xll
 * @date 2018/2/2
 */

public class DecimalFormatUtils {
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static DecimalFormat decimalFormat2 = new DecimalFormat("###################.###########");

    public static String keepTwo(Double d) {
        String s;
        try {
            s = decimalFormat.format(d);
        } catch (Exception e) {
            s = "-";
        }

        if (s.endsWith(".00")) {
            s = s.substring(0, s.length() - 3);
        }

        if (s.contains(".")) {
            if (s.endsWith("0")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }

    public static String keepZero(Double d) {
        String s;
        try {
            s = decimalFormat2.format(d);
        } catch (Exception e) {
            s = "-";
        }

        return s;
    }
}
