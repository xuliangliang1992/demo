package com.xll.mvplib.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;

import com.xll.mvplib.R;
import com.xll.mvplib.dialog.base.BaseDialog;
import com.xll.mvplib.utils.BitmapUtil;
import com.xll.mvplib.view.SignView;

/**
 * @author xll
 * @date 2018/1/1
 */
public class SignDialog extends BaseDialog {

    private SignView mSignView;
    private Button btn1;
    private Button btn2;
    private Button btn3;

    private View.OnClickListener sureClick;

    public SignDialog(Context context, View.OnClickListener sureClick) {
        super(context);
        this.sureClick = sureClick;
        setCancel(false);
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(context, R.layout.sign_dialog, null);
        widthScale(0.9f);
        mSignView = (SignView) view.findViewById(R.id.sv_bank);
        btn1 = (Button) view.findViewById(R.id.btn_1);
        btn2 = (Button) view.findViewById(R.id.btn_2);
        btn3 = (Button) view.findViewById(R.id.btn_3);
        return view;
    }

    @Override
    public boolean setUiBeforeShow() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignView.clear();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sureClick != null) {
                    dismiss();
                    sureClick.onClick(v);
                }
            }
        });
        return false;
    }

    public Bitmap getSignBitmap() {
        Bitmap bitmap = BitmapUtil.viewToBitmap(mSignView);
        return BitmapUtil.scaleBitmap(bitmap, 400);
    }
}
