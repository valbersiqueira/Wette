package com.br.wetter.wetter.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.br.wetter.wetter.R;

public class ProgressBar {
    AlertDialog mDialog;

    public ProgressBar(Context mContext) {
        this.mDialog = dialogProgress(mContext);
    }

    private  AlertDialog dialogProgress(Context mContext) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        mBuilder.setView(mLayoutInflater.inflate(R.layout.alert_progress, null));
        mBuilder.setCancelable(false);
        return mBuilder.create();
    }

    public void hidenDialogo(){
        if(mDialog !=null){
            mDialog.dismiss();
        }
    }

    public void openDialogo(){
        mDialog.show();
    }
}
