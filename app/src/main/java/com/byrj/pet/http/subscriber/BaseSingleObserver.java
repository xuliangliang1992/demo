package com.byrj.pet.http.subscriber;

import com.orhanobut.logger.Logger;
import com.xll.mvplib.subscriber.HttpObserver;
import com.xll.mvplib.subscriber.SubscriberOnErrorListener;

import java.net.SocketTimeoutException;

import io.reactivex.SingleObserver;
import retrofit2.HttpException;

/**
 * @author xll
 * @date 2018/12/4
 */

public abstract class BaseSingleObserver<T> implements SingleObserver<T> {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private HttpObserver httpObserver;
    private SubscriberOnErrorListener onErrorListener;

    /**
     * 错误可以做统一处理，如果不需要统一处理的 自己去覆盖此方法
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        Logger.e(e.getMessage());
        //        DialogManager.getInstance().dismissProgressDialog();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED://未登录
                    httpObserver.httpUnauthorized();
                    break;
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                case GATEWAY_TIMEOUT:
                default:
                    if (onErrorListener != null) {
                        onErrorListener.onError(e);
                    } else {
                        httpObserver.httpException(httpException.code());
                    }
                    break;
            }
        } else if (e instanceof SocketTimeoutException) {
            if (onErrorListener != null) {
                onErrorListener.onError(e);
            } else {
                httpObserver.httpTimeOutException();
            }
        } else {
            if (onErrorListener != null) {
                onErrorListener.onError(e);
            } else {
                String msg = e.getMessage();
                httpObserver.httpOtherException(msg);
            }
        }
    }

    public BaseSingleObserver(HttpObserver httpObserver, SubscriberOnErrorListener onErrorListener) {
        this.httpObserver = httpObserver;
        this.onErrorListener = onErrorListener;
    }
}
