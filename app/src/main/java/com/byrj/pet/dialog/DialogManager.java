package com.byrj.pet.dialog;

import android.content.Context;
import android.util.SparseArray;

import com.xll.mvplib.dialog.MiddleListDialog;
import com.xll.mvplib.dialog.base.BaseDialog;
import com.xll.mvplib.view.ItemClickListener;
import com.xll.mvplib.view.progresshud.ProgressHUD;

/**
 * @author xll
 * @date 2018/12/3
 */

public class DialogManager {
    private static DialogManager dialogManager;

    private ProgressHUD hud;

    public BaseDialog mDialog;

    private DialogManager() {

    }

    public static DialogManager getInstance() {
        if (null == dialogManager) {
            synchronized (DialogManager.class) {
                if (null == dialogManager) {
                    dialogManager = new DialogManager();
                }
            }
        }
        return dialogManager;
    }

    /**
     * 测试地址选择
     *
     * @param context
     * @param title
     * @param listData
     * @param itemClickListener
     */
    public void showAddressDialog(Context context, String title, SparseArray<String> listData, ItemClickListener itemClickListener) {
        mDialog = new MiddleListDialog(context, title, listData, itemClickListener);
        mDialog.show();
    }

    public void showProgressHUD(Context context) {
        dismissProgressHUD();
        hud = ProgressHUD.create(context)
                .setStyle(ProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();
    }

    public void dismissProgressHUD() {
        if (null != hud) {
            hud.dismiss();
            hud = null;
        }
    }
}
