package com.artace.tourism.utils;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Herlambang
 */

public class DialogCustom {

    public static void errorDialog(Context context, String title, String contentText, String confirmText){
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(contentText);
        dialog.setConfirmText(confirmText);
        dialog.show();
    }

    public static SweetAlertDialog progressDialog(Context context, String title, String contentText){
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(contentText);
        return dialog;
    }

    public static SweetAlertDialog progressDialogFragment(Context context){
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("Loading Data");
        return dialog;
    }

}
