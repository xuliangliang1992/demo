package com.byrj.pet.fun.login.code;

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
import com.byrj.pet.fun.login.password.PasswordActivity;
import com.byrj.pet.pet.R;
import com.xll.mvplib.utils.StringUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author xll
 * @date 2018/12/4
 */
public class VerificationCodeFragment extends BaseFragment implements VerificationCodeContract.View {

    @BindView(R.id.et_verification_code)
    TextInputEditText mEtVerificationCode;
    @BindView(R.id.til_verification_code)
    TextInputLayout mTilVerificationCode;
    Unbinder unbinder;
    private VerificationCodeContract.Presenter mPresenter;
    private String phone, code;

    public static VerificationCodeFragment newInstance() {
        return new VerificationCodeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone = getActivity().getIntent().getStringExtra(Constant.INTENT_PHONE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verification_code_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void loginSuccess(Map<String, Object> map) {
        dismissProgressDialog();
        toMainActivity();
    }

    @Override
    public void sendCodeSuccess(Map<String, Object> map) {
        dismissProgressDialog();
    }

    @Override
    public void setPresenter(VerificationCodeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.unSubscriber();
    }

    @OnClick({R.id.tv_password_login, R.id.tv_send_code, R.id.btn_login, R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_password_login:
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_PHONE, phone);
                routeTo(intent, PasswordActivity.class);
                break;
            case R.id.tv_send_code:
                showProgressDialog();
                mPresenter.sendCode(phone);
                break;
            case R.id.btn_login:
                code = mEtVerificationCode.getText().toString().trim();
                mTilVerificationCode.setErrorEnabled(false);
                if(validateVerificationCode(code)){
                    showProgressDialog();
                    mPresenter.login(phone, code, "1");
                }
                break;
            case R.id.tv_agreement:
                break;
        }
    }

    public boolean validateVerificationCode(String code) {
        if (!StringUtil.isStringNull(code)) {
            return true;
        } else {
            mTilVerificationCode.setError("请输入验证码");
            return false;
        }
    }
}
