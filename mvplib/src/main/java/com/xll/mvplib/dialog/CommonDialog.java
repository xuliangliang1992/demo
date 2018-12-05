package com.xll.mvplib.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BaseDialog;
import com.xll.mvplib.utils.StringUtil;

/**
 * 一般的普通框
 * ---------------------------
 * |          title          |
 * |         content         |
 * ---------------------------
 * |    left    |    right   |
 * ---------------------------
 *
 * @author xll
 * @date 2018/1/1
 */
public class CommonDialog extends BaseDialog {

    TextView tvTitle;
    TextView tvContent;
    TextView tvLeft;
    TextView tvRight;
    ImageView imgContent;
    View dialogDivider;
    private String title;
    protected String content;
    private String left;
    private String right;
    private int pic = 0;
    private boolean rightDismiss = true;//控制右边按钮是否关闭当前弹框

    private DialogClickListener clickListener;

    public CommonDialog(Context context, String content, DialogClickListener clickListener) {
        super(context);
        this.content = content;
        this.clickListener = clickListener;
        setCancel(false);
    }

    public CommonDialog(Context context, String content, String right, DialogClickListener clickListener) {
        super(context);
        this.content = content;
        this.right = right;
        this.clickListener = clickListener;
        setCancel(false);
    }

    public CommonDialog(Context context, String content, String right, DialogClickListener clickListener, int picRes) {
        super(context);
        this.content = content;
        this.right = right;
        this.clickListener = clickListener;
        this.pic = picRes;
        setCancel(false);
    }

    public CommonDialog(Context context, String content, String left, String right, DialogClickListener clickListener) {
        super(context);
        this.content = content;
        this.left = left;
        this.right = right;
        this.clickListener = clickListener;
        setCancel(false);
    }

    public CommonDialog(Context context, String content, String left, String right, DialogClickListener clickListener, int picRes) {
        super(context);
        this.content = content;
        this.left = left;
        this.right = right;
        this.clickListener = clickListener;
        this.pic = picRes;
        setCancel(false);
    }

    public CommonDialog(Context context, String title, String content, String left, String right, DialogClickListener clickListener) {
        super(context);
        this.title = title;
        this.content = content;
        this.left = left;
        this.right = right;
        this.clickListener = clickListener;
        setCancel(false);
    }

    public CommonDialog(Context context, String title, String content, String left, String right, DialogClickListener clickListener, boolean rightDismiss) {
        super(context);
        this.title = title;
        this.content = content;
        this.left = left;
        this.right = right;
        this.clickListener = clickListener;
        this.rightDismiss = rightDismiss;
        setCancel(false);
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.common_dialog, null);
        widthScale(0.85f);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        tvContent.setText(content);
        tvLeft.setText(left);
        tvRight = (TextView) view.findViewById(R.id.tv_right);
        tvRight.setText(right);
        imgContent = (ImageView) view.findViewById(R.id.img_content);
        imgContent.setBackgroundResource(pic);
        dialogDivider = view.findViewById(R.id.dialog_divider);
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
                if (rightDismiss) {
                    dismiss();
                }
            }
        });
        return view;
    }

    @Override
    public boolean setUiBeforeShow() {
        if (StringUtil.isStringNull(title)) {
            tvTitle.setVisibility(View.GONE);
        }
        dialogDivider.setVisibility(View.VISIBLE);
        if (StringUtil.isStringNull(left) && !StringUtil.isStringNull(right)) {
            tvLeft.setVisibility(View.GONE);
            dialogDivider.setVisibility(View.GONE);
            tvRight.setBackgroundResource(R.drawable.btn_confirm_exit);
        }
        if (pic != 0) {
            imgContent.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isStringNull(left) && StringUtil.isStringNull(right)) {
            tvLeft.setText(R.string.cancel);
            tvRight.setText(R.string.sure);
        }
        return true;
    }
}
