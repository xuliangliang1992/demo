package com.xll.mvplib.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;

import java.util.Calendar;

/**
 * @author xll
 * @date 2018/1/1
 */
public class SaicButton extends AppCompatButton {

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    private boolean isFirstClick = true;

    public SaicButton(Context context) {
        super(context);
    }

    public SaicButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SaicButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                long currentTime = Calendar.getInstance().getTimeInMillis();
                Logger.i("currentTime is %d", currentTime);
                if (!isFirstClick && currentTime - lastClickTime < MIN_CLICK_DELAY_TIME){
                    return true;//拦截掉该事件 不让向下传递给onclick
                }else{
                    isFirstClick = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isFirstClick = false;
                lastClickTime = Calendar.getInstance().getTimeInMillis();
                Logger.i("lastTime is %d", lastClickTime);

                break;
        }
        return super.onTouchEvent(event);
    }

}
