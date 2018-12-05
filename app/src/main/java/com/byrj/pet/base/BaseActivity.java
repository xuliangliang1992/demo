package com.byrj.pet.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.byrj.pet.pet.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author xll
 * @date 2018/12/3
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    Unbinder unbinder;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ll_right)
    LinearLayout mLlRight;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        unbinder = ButterKnife.bind(this);
    }

    protected void setTitle(String title) {
        mTvTitle.setText(title);
    }

    @Override
    public void setTitle(int resId) {
        if (resId == 0) {
            setTitle("");
        } else {
            setTitle(getResources().getString(resId));
        }
    }

    /**
     * 默认的title bar是展示的
     * 如有页面没有title 用该方法隐藏
     * 隐藏title bar
     */
    public void hideTitleBar() {
        mAppBar.setVisibility(View.GONE);
    }

    /**
     * 隐藏返回键
     */
    protected void hideIvBack() {
        mLlBack.setVisibility(View.GONE);
    }

    public void setRightText(int resId) {
        setRightText(getResources().getString(resId));
    }

    protected void setRightText(String text) {
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(text);
    }

    protected void setRightText(String text, View.OnClickListener onClickListener) {
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(text);
        mTvRight.setOnClickListener(onClickListener);
    }

    protected void setRightImageView(int resId) {
        if (resId == 0) {
            mLlRight.setVisibility(View.GONE);
        } else {
            mLlRight.setVisibility(View.VISIBLE);
            mIvRight.setImageResource(resId);
        }
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }
}
