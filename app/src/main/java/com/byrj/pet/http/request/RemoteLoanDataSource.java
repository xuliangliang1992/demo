package com.byrj.pet.http.request;

import android.support.annotation.Nullable;

import com.byrj.pet.http.AppRetrofit;
import com.byrj.pet.http.HttpFilterFunc;
import com.byrj.pet.http.HttpMapToBean;
import com.byrj.pet.http.HttpUrl;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author xll
 * @date 2018/12/4
 */
public class RemoteLoanDataSource implements LoanDataSource {

    @Nullable
    private static RemoteLoanDataSource INSTANCE;

    private RemoteLoanDataSource() {

    }

    public static RemoteLoanDataSource getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new RemoteLoanDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<Map<String, Object>> login(String phone, String code, String codeId) {
        HashMap<String, Object> params = new HashMap<>(3);
        params.put("phone", phone);
        params.put("code", code);
        params.put("codeId", codeId);
        return new AppRetrofit().getFaceIDService().login(HttpUrl.LOGIN_URL, params)
                .filter(new HttpFilterFunc<Map<String, Object>>())
                .map(new HttpMapToBean<Map<String, Object>>());
    }

    @Override
    public Observable<Map<String, Object>> sendCode(String phone) {
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("phone", phone);
        return new AppRetrofit().getFaceIDService().login(HttpUrl.SEND_CODE_URL, params)
                .filter(new HttpFilterFunc<Map<String, Object>>())
                .map(new HttpMapToBean<Map<String, Object>>());
    }
}