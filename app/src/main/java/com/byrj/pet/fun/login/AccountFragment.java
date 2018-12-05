package com.byrj.pet.fun.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byrj.pet.base.BaseFragment;
import com.byrj.pet.constant.Constant;
import com.byrj.pet.fun.login.code.VerificationCodeActivity;
import com.byrj.pet.pet.R;
import com.xll.mvplib.utils.CheckRegUtil;
import com.xll.mvplib.utils.StringUtil;
import com.xll.mvplib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author xll
 * @date 2018/12/4
 */
public class AccountFragment extends BaseFragment {

    @BindView(R.id.et_phone)
    TextInputEditText mEtPhone;
    @BindView(R.id.til_phone)
    TextInputLayout mTilPhone;
    Unbinder unbinder;
    private String phone;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        phone = mEtPhone.getText().toString().trim();
        mTilPhone.setErrorEnabled(false);
        if (validateAccount(phone)) {
            Intent intent = new Intent();
            intent.putExtra(Constant.INTENT_PHONE, phone);
            routeTo(intent, VerificationCodeActivity.class);
        }
    }

    public boolean validateAccount(String phone) {
        if (StringUtil.isStringNull(phone)) {
            mTilPhone.setError("请输入手机号");
            return false;
        } else if (!CheckRegUtil.isPhoneNum(phone)) {
            mTilPhone.setError("手机号格式不对");
            return false;
        } else {
            return true;
        }
    }

    @OnClick(R.id.tv_agreement)
    public void agreement() {
        ToastUtil.showToast(getActivity(), "跳转协议");
    }
}
