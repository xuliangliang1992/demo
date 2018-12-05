package com.byrj.pet.http.request;

import com.byrj.pet.http.response.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author xll
 * @date 2018/12/4
 */
public interface LoanDataSource {
    /**
     * 登录
     *
     * @param email 登录用户邮箱地址
     * @param pwd   密码
     * @return
     */
    Observable<Map<String, Object>> login(String phone, String code, String codeId);

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    Observable<Map<String, Object>> sendCode(String phone);

}
