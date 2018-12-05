package com.byrj.pet.fun.login.password;

import com.xll.mvplib.base.BasePresenter;
import com.xll.mvplib.base.BaseView;

import java.util.Map;

/**
 * @author xll
 * @date 2018/12/5
 */
public class PasswordContract {

    interface View extends BaseView<Presenter> {

        /**
         * 登录成功
         *
         * @param userBaseResponse
         */
        void loginSuccess(Map<String, Object> userBaseResponse);

    }

    interface Presenter extends BasePresenter {

        /**
         * 登录
         *
         * @param email
         * @param pwd
         */
        void login(String phone, String code, String codeId);

    }
}
