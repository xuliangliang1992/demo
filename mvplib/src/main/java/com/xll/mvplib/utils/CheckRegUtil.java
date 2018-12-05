package com.xll.mvplib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验
 *
 * @author xll
 * @date 2018/1/1
 */
public class CheckRegUtil {

    private static final String REG_PHONE_NUM = "^1\\d{10}$";
    private static final String REG_PASSWORD = "^[a-zA-Z0-9]{8,20}$";
    private static final String REG_EMAIL = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

    /**
     * 手机号码正则校验
     *
     * @param phoneNo
     * @return
     */
    public static boolean isPhoneNum(String phoneNo) {
        Pattern p = Pattern.compile(REG_PHONE_NUM);
        Matcher m = p.matcher(phoneNo);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        return Pattern.matches(REG_EMAIL, email);
    }

    /**
     * 密码规则 8到20 数字字母组合
     * @param password 密码
     * @return
     */
    public static boolean isPasswordValidate(String password){
        Pattern p = Pattern.compile(REG_PASSWORD);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 身份证校验
     * @param idNo 身份证号码
     * @return
     */
    public static boolean checkIdNo(String idNo){
//        Pattern p = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");//15位身份证
//        Pattern p2 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");//18位身份证
//        Matcher m = p.matcher(idNo);
//        Matcher m2 = p2.matcher(idNo);
//        return m.matches() || m2.matches();
        return IdCardValidator.isValidatedIdCard(idNo);
    }

}
