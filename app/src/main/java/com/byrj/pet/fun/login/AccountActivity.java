package com.byrj.pet.fun.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.byrj.pet.base.BaseActivity;
import com.byrj.pet.pet.R;
import com.xll.mvplib.utils.ActivityUtils;

/**
 * 输入账号
 *
 * @author xll
 * @date 2018/12/4
 */
public class AccountActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AccountFragment fragment = (AccountFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (null == fragment) {
            fragment = AccountFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fl_content);
        }
    }
}
