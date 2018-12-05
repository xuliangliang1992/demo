package com.xll.mvplib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xll.mvplib.R;

/**签名板
 * @author xll
 * @date 2018/1/1
 */
public class SignView extends View {

    private int backgroundColor;
    private int paintColor;
    private int paintWidth;

    private Paint textPaint;
    private Path mPath;
    private Canvas mCanvas;

    private float currentX;
    private float currentY;

    private boolean isDraw = false;

    public SignView(Context context) {
        this(context, null);
    }

    public SignView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SignView);
        backgroundColor = a.getColor(R.styleable.SignView_signBackground, Color.WHITE);
        paintColor = a.getColor(R.styleable.SignView_paintColor, Color.BLACK);
        paintWidth = a.getDimensionPixelSize(R.styleable.SignView_paintWidth, 5);
        a.recycle();

        textPaint = new Paint();
        mPath = new Path();
        setBackgroundColor(backgroundColor);
        textPaint.setColor(paintColor);
        textPaint.setStrokeWidth(paintWidth);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;
        canvas.drawPath(mPath, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (getParent()!= null && getParent().getParent() != null){
                    getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
                currentX = touchX;
                currentY = touchY;
                mPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = touchX;
                currentY = touchY;
                mPath.quadTo(currentX, currentY, touchX, touchY);
                isDraw = true;
                break;
            case MotionEvent.ACTION_UP:
                if (getParent()!= null && getParent().getParent() != null){
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
//                    mCanvas.drawPath(mPath, textPaint);
                break;
        }
        invalidate();
        return true;
    }

    public boolean getDrawState() {
        return isDraw;
    }

    public void clear(){
        isDraw = false;
        if (mCanvas != null){
            mPath.reset();
//            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            invalidate();
        }
    }

}
