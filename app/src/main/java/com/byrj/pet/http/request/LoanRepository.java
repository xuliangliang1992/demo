package com.byrj.pet.http.request;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author xll
 * @date 2018/12/4
 */
public class LoanRepository implements LoanDataSource {

    @Nullable
    private static LoanRepository INSTANCE;

    private RemoteLoanDataSource mRemoteLoanDataSource;

    private LoanRepository(RemoteLoanDataSource remoteLoanDataSource) {
        this.mRemoteLoanDataSource = remoteLoanDataSource;
    }

    public static LoanRepository getInstance(@NonNull RemoteLoanDataSource remoteLoanDataSource) {
        if (null == INSTANCE) {
            INSTANCE = new LoanRepository(remoteLoanDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Observable<Map<String, Object>> login(String phone, String code, String codeId) {
        return mRemoteLoanDataSource.login(phone, code, codeId);
    }

    @Override
    public Observable<Map<String, Object>> sendCode(String phone) {
        return mRemoteLoanDataSource.sendCode(phone);
    }
}
