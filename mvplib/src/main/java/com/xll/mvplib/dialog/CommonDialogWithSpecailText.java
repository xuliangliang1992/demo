package com.xll.mvplib.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BaseDialog;
import com.xll.mvplib.utils.StringUtil;

/**
 * 特殊字段需要改颜色，现在都是蓝色，如果后期要可以改为任意颜色 可以再加一个参数
 *
 * @author xll
 * @date 2018/1/1
 */
public class CommonDialogWithSpecailText extends BaseDialog {
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvLeft;
    private TextView tvRight;

    private String title;
    private String content;
    private String specailStr;
    private String left;
    private String right;
    private DialogClickListener clickListener;

    /**
     * method execute order:
     * show:constrouctor---show---oncreate---onStart---onAttachToWindow
     * dismiss:dismiss---onDetachedFromWindow---onStop
     *
     * @param context
     */
    public CommonDialogWithSpecailText(Context context) {
        super(context);
    }

    public CommonDialogWithSpecailText(Context context, String content, String specailStr) {
        super(context);
        this.content = content;
        this.specailStr = specailStr;
    }

    public CommonDialogWithSpecailText(Context context, String content, String specailStr, String left, String right, DialogClickListener clickListener) {
        super(context);
        this.content = content;
        this.specailStr = specailStr;
        this.left = left;
        this.right = right;
        this.clickListener = clickListener;
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.common_dialog, null);
        widthScale(0.85f);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        tvRight = (TextView) view.findViewById(R.id.tv_right);
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.leftClickListener();
                }
                dismiss();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.rightClickListener();
                }
                dismiss();
            }
        });
        return view;
    }

    @Override
    public boolean setUiBeforeShow() {
        if (StringUtil.isStringNull(title)) {
            tvTitle.setVisibility(View.GONE);
        }
        if(content.contains(specailStr)){
            int start = content.indexOf(specailStr);
            int end = start + specailStr.length();
            SpannableStringBuilder style=new SpannableStringBuilder(content);
            style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.base_blue)),start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            tvContent.setText(style);
        }else {
            tvContent.setText(content);
        }
        tvLeft.setText(left);
        tvRight.setText(right);

        if (StringUtil.isStringNull(left) && !StringUtil.isStringNull(right)) {
            tvLeft.setVisibility(View.GONE);
            tvRight.setBackgroundResource(R.drawable.btn_confirm_exit);
        }

        if (StringUtil.isStringNull(left) && StringUtil.isStringNull(right)) {
            tvLeft.setText(R.string.cancel);
            tvRight.setText(R.string.sure);
        }
        return true;
    }
}
