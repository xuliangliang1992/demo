package com.xll.mvplib.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BaseDialog;
import com.xll.mvplib.utils.EditTextFormat;
import com.xll.mvplib.utils.StringUtil;


/**
 * /**
 *
 * @author :wcz
 *         担保人面签
 *         ---------------------------
 *         |          title          |
 *         |          EditText       |
 *         ---------------------------
 *         |    left    |    right   |
 *         ---------------------------
 *         Created by wujinpeng on 2016/9/6.
 */
public class EnsureSignDialog extends BaseDialog {

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

    private EditText etEnsurePhoneNo;//担保人手机号

    private DialogClickListener clickListener;

    public EnsureSignDialog(Context context, int etHint, String left, String right, DialogClickListener clickListener) {
        super(context);
        this.mContext = context;
        this.etHint = etHint;
        this.left = left;
        this.right = right;
        this.clickListener = clickListener;
        setCancel(false);
    }

    public EnsureSignDialog(Context context, String title, int etHint, String left, String right, DialogClickListener clickListener) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.etHint = etHint;
        this.left = left;
        this.right = right;
        this.clickListener = clickListener;
        setCancel(false);
    }

    public EnsureSignDialog(Context context, int title, int etHint, int left, int right, DialogClickListener clickListener) {
        super(context);
        this.mContext = context;
        this.title = context.getString(title);
        this.etHint = etHint;
        this.left = context.getString(left);
        this.right = context.getString(right);
        this.clickListener = clickListener;
        setCancel(false);
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.ensure_sign_dialog, null);
        widthScale(0.85f);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        etPhoneNo = (EditText) view.findViewById(R.id.et_phoneNo);
        etPhoneNo.setHint(etHint);
        EditTextFormat.phoneNumAddSpace(etPhoneNo);

        etEnsurePhoneNo = (EditText) view.findViewById(R.id.et_ensure_phoneNo);
        EditTextFormat.phoneNumAddSpace(etEnsurePhoneNo);

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

        etPhoneNo.addTextChangedListener(etTextWatcher);
        etEnsurePhoneNo.addTextChangedListener(etTextWatcher);
        return view;
    }

    private TextWatcher etTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if ("手机号码格式不正确".contains(tvErrMsg.getText())) {
                tvErrMsg.setVisibility(View.GONE);
                tvErrMsg.setTextColor(mContext.getResources().getColor(R.color.base_black_2));
            } else {
                tvErrMsg.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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

    public String getPhoneNo(){
        return EditTextFormat.getFormatNo(etPhoneNo);
    }

    public String getGuarantorPhoneNo(){
        return EditTextFormat.getFormatNo(etEnsurePhoneNo);
    }

    public void showMessageTv(int text){
        tvErrMsg.setVisibility(View.VISIBLE);
        tvErrMsg.setTextColor(mContext.getResources().getColor(R.color.base_red));
        tvErrMsg.setText(text);
    }
}
