package com.xll.mvplib.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BaseDialog;
import com.xll.mvplib.utils.StringUtil;

/**
 * /**
 *
 * @author :wcz
 *         带有输入框的dialog
 *         功能入口关联
 *         ---------------------------
 *         |          title          |
 *         |          EditText       |
 *         ---------------------------
 *         |    left    |    right   |
 *         ---------------------------
 *         Created by wujinpeng on 2016/9/6.
 */
public class ModifyNameDialog extends BaseDialog {

    TextView tvTitle;
    public EditText etPhoneNo;
    public TextView tvErrMsg;
    public View dialogDivider;
    TextView tvLeft;
    TextView tvRight;
    ImageView img_close;
    private String title;
    private int etHint;
    private String left;
    private String right;
    private Context mContext;

    private DialogClickListener clickListener;

    public ModifyNameDialog(Context context, int etHint, String left, String right, DialogClickListener clickListener) {
        this(context, "", etHint, left, right, clickListener);
    }

    public ModifyNameDialog(Context context, int title, int etHint, int left, int right, DialogClickListener clickListener) {
        this(context, context.getString(title), etHint, context.getString(left), context.getString(right), clickListener);
    }

    public ModifyNameDialog(Context context, String title, int etHint, DialogClickListener clickListener) {
        this(context, title, etHint, "", "", clickListener);
    }

    public ModifyNameDialog(Context context, String title, int etHint, String left, String right, DialogClickListener clickListener) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.etHint = etHint;
        this.left = left;
        this.right = right;
        this.clickListener = clickListener;
        setCancel(false);
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.modify_name_dialog, null);
        widthScale(0.85f);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        etPhoneNo = (EditText) view.findViewById(R.id.et_phoneNo);
        etPhoneNo.setHint(etHint);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        tvLeft.setText(left);
        tvRight = (TextView) view.findViewById(R.id.tv_right);
        tvRight.setText(right);
        img_close = (ImageView) view.findViewById(R.id.img_close);
        tvErrMsg = (TextView) view.findViewById(R.id.tv_errMsg);
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
//                dismiss();
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        if (StringUtil.isStringNull(left) && !StringUtil.isStringNull(right)) {
            tvLeft.setVisibility(View.GONE);
            dialogDivider.setVisibility(View.GONE);
            tvRight.setBackgroundResource(R.drawable.btn_confirm_exit);
        }
        if (StringUtil.isStringNull(left) && StringUtil.isStringNull(right)) {
            tvLeft.setText("取消");
            tvRight.setText("确定");
        }
        return true;
    }

    public String getEditText(){
        return etPhoneNo.getText().toString().trim();
    }
    public void setEditText(String editText){
        etPhoneNo.setText(editText);
        etPhoneNo.setSelection(editText.length());
    }

    public void showMessageTv(int text){
        tvErrMsg.setVisibility(View.VISIBLE);
        tvErrMsg.setTextColor(ContextCompat.getColor(context, R.color.base_red));
        tvErrMsg.setText(text);
    }
}
