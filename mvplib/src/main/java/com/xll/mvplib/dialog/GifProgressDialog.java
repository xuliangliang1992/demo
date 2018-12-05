package com.xll.mvplib.dialog;

import android.content.Context;
import android.view.View;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BaseDialog;
import com.xll.mvplib.view.GifView;

/**
 * @author xll
 * @date 2018/1/1
 */
public class GifProgressDialog extends BaseDialog {

    private int gifRes;

    /**
     * method execute order:
     * show:constrouctor---show---oncreate---onStart---onAttachToWindow
     * dismiss:dismiss---onDetachedFromWindow---onStop
     *
     * @param context
     */
    public GifProgressDialog(Context context) {
        super(context);
    }

    public GifProgressDialog(Context context, int gifResId) {
        super(context);
        this.gifRes = gifResId;
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.gif_progress_dialog, null);
        if (gifRes != 0) {
            GifView gifView = (GifView) view.findViewById(R.id.gif_view);
            gifView.setMovieResource(gifRes);
        }
        return view;
    }

    @Override
    public boolean setUiBeforeShow() {
        return false;
    }
}
