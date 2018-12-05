package com.byrj.pet.http;

import android.os.Handler;
import android.os.Looper;

import com.byrj.pet.base.MainApplication;
import com.byrj.pet.dialog.DialogManager;
import com.byrj.pet.http.response.BaseResponse;
import com.xll.mvplib.utils.ToastUtil;

import io.reactivex.functions.Predicate;

/**
 * 过滤器
 *
 * @author xll
 * @date 2018/12/4
 */
public class HttpFilterFunc<T> implements Predicate<BaseResponse<T>> {

    @Override
    public boolean test(BaseResponse<T> map) throws Exception {
        if (map == null) {
            return false;
        }
        if (!map.isSuccess()) {
            final String msg = map.getMsg();
            new Handler(Looper.getMainLooper()).post(
                    new Runnable() {
                        @Override
                        public void run() {
                            DialogManager.getInstance().dismissProgressHUD();
                            ToastUtil.showToast(MainApplication.getInstance().getApplicationContext(), msg);
                        }
                    }
            );
            return false;
        }
        return true;
    }
}
