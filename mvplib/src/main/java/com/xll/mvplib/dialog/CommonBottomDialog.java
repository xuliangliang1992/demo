package com.xll.mvplib.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BottomBaseDialog;
import com.xll.mvplib.utils.StringUtil;

/**
 * 一般的普通框
 *
 * @author xll
 * @date 2018/1/1
 */
public class CommonBottomDialog extends BottomBaseDialog {

    TextView tvTitle;
    TextView tvContent;
    ImageView imgContent;
    private String title;
    protected String content;

    public CommonBottomDialog(Context context, String content) {
        super(context);
        this.content = content;
        setCancel(false);
    }

    public CommonBottomDialog(Context context, String title, String content) {
        super(context);
        this.content = content;
        this.title = title;
        setCancel(false);
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.common_bottom_dialog, null);
        widthScale(1f);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvContent.setText(content);
        imgContent = (ImageView) view.findViewById(R.id.img_content);
        return view;
    }

    @Override
    public boolean setUiBeforeShow() {
        if (StringUtil.isStringNull(title)) {
            tvTitle.setVisibility(View.GONE);
        }
        return true;
    }
}
