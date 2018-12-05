package com.byrj.pet.fun.login.password;

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
import com.byrj.pet.pet.R;
import com.xll.mvplib.utils.StringUtil;
import com.xll.mvplib.utils.ToastUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author xll
 * @date 2018/12/5
 */
public class PasswordFragment extends BaseFragment implements PasswordContract.View {

    @BindView(R.id.et_password)
    TextInputEditText mEtPassword;
    @BindView(R.id.til_password)
    TextInputLayout mTilPassword;
    Unbinder unbinder;
    private PasswordContract.Presenter mPresenter;
    private String phone, password;

    public static PasswordFragment newInstance() {
        return new PasswordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone = getActivity().getIntent().getStringExtra(Constant.INTENT_PHONE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.unSubscriber();
    }

    @OnClick({ R.id.tv_forget, R.id.btn_login, R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
           case R.id.tv_forget:
                ToastUtil.showToast(getActivity(), "跳转忘记密码");
                break;
            case R.id.btn_login:
                password = mEtPassword.getText().toString().trim();
                mTilPassword.setErrorEnabled(false);
                if (validatePassword(password)) {
                    showProgressDialog();
                    mPresenter.login(phone,password,"1");
                }
                break;
            case R.id.tv_agreement:
                ToastUtil.showToast(getActivity(), "跳转协议");
                break;
        }
    }

    @Override
    public void loginSuccess(Map<String, Object> userBaseResponse) {
        dismissProgressDialog();
        toMainActivity();
    }

    @Override
    public void setPresenter(PasswordContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private boolean validatePassword(String password) {
        if (StringUtil.isStringNull(password)) {
            mTilPassword.setError("密码不能为空");
            return false;
        }
        if (password.length() < 4 || password.length() > 10) {
            mTilPassword.setError("密码长度必须4-10位");
            return false;
        } else
            return true;
    }
}
