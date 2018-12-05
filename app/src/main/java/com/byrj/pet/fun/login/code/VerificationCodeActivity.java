package com.byrj.pet.fun.login.code;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.byrj.pet.base.BaseActivity;
import com.byrj.pet.injection.Injection;
import com.byrj.pet.pet.R;
import com.xll.mvplib.utils.ActivityUtils;

/**
 * 验证码登录
 *
 * @author xll
 * @date 2018/12/5
 */
public class VerificationCodeActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("验证码登录");

        VerificationCodeFragment fragment = (VerificationCodeFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (null == fragment) {
            fragment = VerificationCodeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fl_content);
        }
        new VerificationCodePresenter(Injection.provideLoanRepository(),fragment,Injection.provideSchedulerProvider());
    }
}
