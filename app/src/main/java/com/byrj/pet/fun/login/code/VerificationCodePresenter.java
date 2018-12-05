package com.byrj.pet.fun.login.code;

import com.byrj.pet.http.request.LoanDataSource;
import com.byrj.pet.http.subscriber.SaicObserver;
import com.xll.mvplib.schedulers.BaseSchedulerProvider;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author xll
 * @date 2018/12/5
 */
public class VerificationCodePresenter implements VerificationCodeContract.Presenter {

    private VerificationCodeContract.View mView;
    private CompositeDisposable mCompositeSubscription;
    private BaseSchedulerProvider mSchedulerProvider;
    private LoanDataSource mLoanDataSource;

    VerificationCodePresenter(LoanDataSource loanDataSource, VerificationCodeContract.View view, BaseSchedulerProvider schedulerProvider) {
        this.mView = view;
        this.mLoanDataSource = loanDataSource;
        this.mSchedulerProvider = schedulerProvider;
        mView.setPresenter(this);
        mCompositeSubscription = new CompositeDisposable();
    }

    @Override
    public void sendCode(String phone) {
        mCompositeSubscription.clear();
        mLoanDataSource.sendCode(phone)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SaicObserver<Map<String, Object>>(mView) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeSubscription.add(d);
                    }

                    @Override
                    public void onNext(Map<String, Object> map) {
                        mView.sendCodeSuccess(map);
                    }
                });
    }


    @Override
    public void login(String phone, String code, String codeId) {
        mCompositeSubscription.clear();
        mLoanDataSource.login(phone, code, codeId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SaicObserver<Map<String, Object>>(mView) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeSubscription.add(d);
                    }

                    @Override
                    public void onNext(Map<String, Object> userBaseResponse) {
                        mView.loginSuccess(userBaseResponse);
                    }
                });
    }

    @Override
    public void subscriber() {

    }

    @Override
    public void unSubscriber() {
        mCompositeSubscription.clear();
    }
}
