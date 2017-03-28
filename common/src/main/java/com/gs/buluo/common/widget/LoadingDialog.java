package com.gs.buluo.common.widget;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog {

	private ProgressDialog mDialog;
	private static LoadingDialog instance = null;

	public static synchronized LoadingDialog getInstance() {
		if (instance == null) {
			instance = new LoadingDialog();
		}
		return instance;
	}
	
	public void show(Context context, String message, boolean cancleable){
		mDialog =  ProgressDialog.show(context, null, message);
		mDialog.setCancelable(cancleable);
		mDialog.show();
	}
	public void show(Context context, int message, boolean cancleable){
		mDialog =  ProgressDialog.show(context, null, context.getResources().getString(message));
		mDialog.setCancelable(cancleable);
		mDialog.show();
	}

	public void dismissDialog() {
		try {
			if (mDialog != null && mDialog.isShowing()) {
				mDialog.dismiss();
			}
		} catch (Exception e) {}
	}

	public boolean isShowing(){
		if (mDialog==null)
			return false;
		return mDialog.isShowing();
	}
}
