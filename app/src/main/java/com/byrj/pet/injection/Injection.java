package com.byrj.pet.injection;


import com.byrj.pet.http.request.LoanRepository;
import com.byrj.pet.http.request.RemoteLoanDataSource;
import com.xll.mvplib.schedulers.BaseSchedulerProvider;
import com.xll.mvplib.schedulers.SchedulerProvider;

/**
 * @author xll
 * @date 2018/12/4
 */
public class Injection {

    public static LoanRepository provideLoanRepository() {
        return LoanRepository.getInstance(RemoteLoanDataSource.getInstance());
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
