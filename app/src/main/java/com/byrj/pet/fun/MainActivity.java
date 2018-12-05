package com.byrj.pet.fun;

import android.os.Bundle;

import com.byrj.pet.base.BaseActivity;
import com.byrj.pet.pet.R;
import com.xll.mvplib.utils.ActivityUtils;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (null == mainFragment) {
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.fl_content);
        }
    }
}
