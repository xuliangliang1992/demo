package com.xll.mvplib.base;

import com.xll.mvplib.subscriber.HttpObserver;

/**
 * @author xll
 * @date 2018/1/1
 */
public interface BaseView<T> extends HttpObserver {

    void setPresenter(T presenter);

}
