package com.xll.mvplib.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BaseDialog;

/**
 * @author xll
 * @date 2018/1/1
 */
public class MentionDialog extends BaseDialog {

    private int imageId;
    private String message;

    public MentionDialog(Context context) {
        super(context);
    }

    public MentionDialog(Context context, int imageId, String message) {
        super(context);
        this.imageId = imageId;
        this.message = message;
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.mention_dialog, null);
        widthScale(0.75f);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_mention_view);
        TextView textView = (TextView) view.findViewById(R.id.tv_message);
        imageView.setBackgroundResource(imageId);
        textView.setText(message);
        return view;
    }

    @Override
    public boolean setUiBeforeShow() {
        return false;
    }
}
