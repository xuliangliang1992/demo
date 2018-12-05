package com.xll.mvplib.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * @author xll
 * @date 2018/1/1
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
