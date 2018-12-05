package com.xll.mvplib.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.xll.mvplib.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xll
 * @date 2018/1/1
 */
public class TimeButton extends AppCompatButton {
    private long lenght = 60 * 1000;// 倒计时长度,这里给了默认60秒
    public String textafter = "S";
    public String textbefore = "重新发送";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    //	private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    public long time;
    Map<String, Long> map = new HashMap<String, Long>();

    private MyHandler mHandler;

    public TimeButton(Context context) {
        super(context);
//		setOnClickListener(this);
        mHandler = new MyHandler(this);

    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
//		setOnClickListener(this);
        mHandler = new MyHandler(this);
    }

    private static class MyHandler extends Handler {

        WeakReference<TimeButton> mContextWeakReference;

        MyHandler(TimeButton context) {
            mContextWeakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            final TimeButton context = mContextWeakReference.get();
            if (null == context) {
                return;
            }
            context.setText(context.time / 1000 + context.textafter);
            context.time -= 1000;
            if (context.time < 0) {
                context.setEnabled(true);
                context.setText(context.textbefore);
                context.setTextColor(ContextCompat.getColor(context.getContext(), R.color.base_blue));
                context.setBackground(ContextCompat.getDrawable(context.getContext(), R.drawable.btn_verify_selector));
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

//	@Override
//	public void setOnClickListener(OnClickListener l) {
//		if (l instanceof TimeButton) {
//			super.setOnClickListener(l);
//		} else
//			this.mOnclickListener = l;
//	}

    //	@Override
    public void onClick() {
//		if (mOnclickListener != null)
//			mOnclickListener.onClick(v);
        initTimer();
        this.setText(time / 1000 + textafter);
        this.setBackgroundColor(getContext().getResources().getColor(R.color.light_grey));
        this.setTextColor(getContext().getResources().getColor(R.color.white));
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
     * 和activity的onCreate()方法同步
     */
    public void onCreate(Bundle bundle) {
        if (map == null)
            return;
        if (map.size() <= 0)// 这里表示没有上次未完成的计时
            return;
        long time = System.currentTimeMillis() - map.get(CTIME)
                - map.get(TIME);
        map.clear();
        if (time > 0)
            return;
        else {
            initTimer();
            this.time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setText(time + textafter);
            this.setBackgroundColor(getContext().getResources().getColor(R.color.light_grey));
            this.setEnabled(false);
        }
    }

    /**
     * 设置计时时候显示的文本
     */
    public TimeButton setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }

    /**
     * 设置点击之前的文本
     */
    public TimeButton setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param lenght 时间 默认毫秒
     * @return
     */
    public TimeButton setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }
}