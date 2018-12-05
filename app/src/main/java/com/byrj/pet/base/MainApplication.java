package com.byrj.pet.base;

import com.xll.mvplib.base.APP;

/**
 * @author xll
 * @date 2018/12/3
 */
public class MainApplication extends APP {

    private static MainApplication instance;
    private static boolean isLogin;
    private static boolean isChangeLan;
    private static boolean isChangeLanCate;

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        MainApplication.isLogin = isLogin;
    }

    public static boolean isIsChangeLan() {
        return isChangeLan;
    }

    public static void setIsChangeLan(boolean isChangeLan) {
        MainApplication.isChangeLan = isChangeLan;
    }

    public static boolean isIsChangeLanCate() {
        return isChangeLanCate;
    }

    public static void setIsChangeLanCate(boolean isChangeLanCate) {
        MainApplication.isChangeLanCate = isChangeLanCate;
    }
}
