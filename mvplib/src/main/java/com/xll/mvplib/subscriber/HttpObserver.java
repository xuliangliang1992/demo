package com.xll.mvplib.subscriber;

/**
 * @author xll
 * @date 2018/1/1
 */

public interface HttpObserver {

    void httpUnauthorized();

    void httpException(int code);

    void httpTimeOutException();

    void httpOtherException(String message);
}
