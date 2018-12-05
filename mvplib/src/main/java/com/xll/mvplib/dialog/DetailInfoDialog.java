package com.xll.mvplib.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BaseDialog;
import com.xll.mvplib.utils.StringUtil;

import java.util.Map;

import butterknife.ButterKnife;

/**
 * @author xll
 * @date 2018/1/1
 */
public class DetailInfoDialog extends BaseDialog {
    public static final int DEFAULT_NON_PIC = 0;

    TextView tvName;
    TextView tvPhoneNo;
    TextView tvIDCard;
    public TextView tvErrMsg;
    TextView tvLeft;
    TextView tvRight;

    private DialogClickListener clickListener;
    private String name;
    private String phoneNo;
    private String idCard;

    public DetailInfoDialog(Context context, String name, String phoneNo, String idCard, DialogClickListener clickListener) {
        super(context);
        this.clickListener = clickListener;
        this.name = name;
        this.phoneNo = phoneNo;
        this.idCard = idCard;
        setCancel(false);
    }

    public DetailInfoDialog(Context context, @NonNull Map<String, String> map, DialogClickListener clickListener) {
        super(context);
        this.clickListener = clickListener;
        this.name = map.get("name");
        this.phoneNo = map.get("phone");
        this.idCard = map.get("idNumber");
        setCancel(false);

    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.detail_info_dialog, null);
        ButterKnife.bind(this, view);
        widthScale(0.85f);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        tvRight = (TextView) view.findViewById(R.id.tv_right);
        tvErrMsg = (TextView) view.findViewById(R.id.tv_errMsg);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvName.setText(StringUtil.hideName(name));
        tvPhoneNo = (TextView) view.findViewById(R.id.tv_phoneNo);
        tvPhoneNo.setText(StringUtil.hideMobile(phoneNo));
        tvIDCard = (TextView) view.findViewById(R.id.tv_IdCard);
        tvIDCard.setText(StringUtil.hideIdNumber(idCard));
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
        return true;
    }
}
