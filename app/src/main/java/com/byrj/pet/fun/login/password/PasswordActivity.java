package com.byrj.pet.fun.login.password;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.byrj.pet.base.BaseActivity;
import com.byrj.pet.injection.Injection;
import com.byrj.pet.pet.R;
import com.xll.mvplib.utils.ActivityUtils;

/**
 * 密码登录
 *
 * @author xll
 * @date 2018/12/5
 */
public class PasswordActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("密码登录");

        PasswordFragment fragment = (PasswordFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (null == fragment) {
            fragment = PasswordFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fl_content);
        }

        new PasswordPresenter(Injection.provideLoanRepository(),fragment,Injection.provideSchedulerProvider());
    }
}
