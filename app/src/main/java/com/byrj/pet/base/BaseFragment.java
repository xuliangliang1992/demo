package com.byrj.pet.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;

import com.byrj.pet.constant.Constant;
import com.byrj.pet.dialog.DialogManager;
import com.byrj.pet.fun.MainActivity;
import com.byrj.pet.pet.R;
import com.xll.mvplib.subscriber.HttpObserver;
import com.xll.mvplib.utils.ToastUtil;

/**
 * @author xll
 * @date 2018/12/3
 */

public abstract class BaseFragment extends Fragment implements HttpObserver {

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 页面跳转公共方法
     * @param clazz 需要跳转的页面
     */
    public void routeTo(Class<? extends BaseActivity> clazz){
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        }else{
            startActivity(intent);
        }
    }

    /**
     * 页面跳转公共方法
     * @param intent intent
     * @param clazz 需要跳转的页面
     */
    public void routeTo(Intent intent, Class<? extends Activity> clazz){
        intent.setClass(getActivity(), clazz);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        }else{
            startActivity(intent);
        }
    }

    public void toLoginActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.setClass(getActivity(), AccountActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void toMainActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void showProgressDialog() {
        DialogManager.getInstance().showProgressHUD(getActivity());
    }

    public void dismissProgressDialog() {
        DialogManager.getInstance().dismissProgressHUD();
    }

    @Override
    public void httpUnauthorized() {
        Intent intent = new Intent();
//        intent.setClass(getActivity(), AccountActivity.class);
        startActivityForResult(intent, Constant.REQUEST_LOGIN);
    }

    @Override
    public void httpException(int code) {
        ToastUtil.showToast(getContext(), "errorCode = " + code + "网络请求出错，请检查网络设置或联系系统管理员");
    }

    @Override
    public void httpTimeOutException() {
        ToastUtil.showToast(getContext(), R.string.time_out);
    }

    @Override
    public void httpOtherException(String message) {
        ToastUtil.showToast(getActivity(), R.string.net_error);
    }

}
