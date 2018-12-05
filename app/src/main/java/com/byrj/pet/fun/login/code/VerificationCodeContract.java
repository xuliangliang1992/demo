package com.byrj.pet.fun.login.code;

import com.xll.mvplib.base.BasePresenter;
import com.xll.mvplib.base.BaseView;

import java.util.Map;

/**
 * @author xll
 * @date 2018/12/5
 */
public class VerificationCodeContract {

    interface View extends BaseView<Presenter> {

        /**
         * 登录成功
         *
         * @param map
         */
        void loginSuccess(Map<String, Object> map);

        /**
         * 发送成功
         *
         * @param map
         */
        void sendCodeSuccess(Map<String, Object> map);

    }

    interface Presenter extends BasePresenter {

        /**
         * 发送验证码
         *
         * @param phone 手机号
         */
        void sendCode(String phone);

        /**
         * 登录
         *
         * @param phone
         * @param code
         * @param codeId
         */
        void login(String phone, String code, String codeId);

    }
}
