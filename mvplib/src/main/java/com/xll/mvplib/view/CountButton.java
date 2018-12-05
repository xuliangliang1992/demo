package com.xll.mvplib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.Button;

import com.xll.mvplib.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 时间倒计时Button控件
 *
 * @author xll
 * @date 2018/1/1
 */
@SuppressLint("AppCompatCustomView")
public class CountButton extends Button {
    private long lenght = 60 * 1000;// 倒计时长度,这里给了默认60秒
    public String textAfter = "S";
    public String textBefore = "重新发送";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private Timer t;
    private TimerTask tt;
    public long time;
    Map<String, Long> map = new HashMap<String, Long>();

    private MyHandler mHandler;

    public CountButton(Context context) {
        super(context);
        mHandler = new MyHandler(this);

    }

    public CountButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new MyHandler(this);
    }

    private static class MyHandler extends Handler {

        WeakReference<CountButton> mContextWeakReference;

        MyHandler(CountButton context) {
            mContextWeakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            final CountButton context = mContextWeakReference.get();
            if (null == context) {
                return;
            }
            context.setText(context.time / 1000 + context.textAfter);
            context.time -= 1000;
            if (context.time < 0) {
                context.setEnabled(true);
                context.setText(context.textBefore);
                context.setTextColor(ContextCompat.getColor(context.getContext(), R.color.base_blue));
//				context.setBackground(ContextCompat.getDrawable(context.getContext(), R.color.white));
                context.clearTimer();
            }
            super.handleMessage(msg);
        }
    }


    private void initTimer() {
        time = lenght;
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                mHandler.sendEmptyMessage(0x01);
            }
        };
    }

    private void clearTimer() {
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    public void click() {
        initTimer();
        this.setText(time / 1000 + textAfter);
//		this.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        this.setTextColor(getContext().getResources().getColor(R.color.base_black_9));
        this.setEnabled(false);
        t.schedule(tt, 0, 1000);
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        if (map == null)
            map = new HashMap<String, Long>();
        map.put(TIME, time);
        map.put(CTIME, System.currentTimeMillis());
        clearTimer();
    }

    /**
     * 设置计时时候显示的文本
     */
    public CountButton setTextAfter(String text1) {
        this.textAfter = text1;
        return this;
    }

    /**
     * 设置点击之前的文本
     */
    public CountButton setTextBefore(String text0) {
        this.textBefore = text0;
        this.setText(textBefore);
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param lenght 时间 默认毫秒
     * @return
     */
    public CountButton setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }

}