package com.xll.mvplib.subscriber;


/**
 * @author xll
 * @date 2018/9/20
 */

public interface FilterHandlerListener {
    /**
     * 处理filter code!=0
     *
     * @param code
     */
    void handleFilter(int code,String msg);
}
