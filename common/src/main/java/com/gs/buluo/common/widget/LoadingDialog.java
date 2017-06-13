package com.gs.buluo.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gs.buluo.common.R;

public class LoadingDialog {

    private Dialog mDialog;
    private static LoadingDialog instance = null;

    public static synchronized LoadingDialog getInstance() {
        if (instance == null) {
            instance = new LoadingDialog();
        }
        return instance;
    }

    public void show(Context context, String message, boolean cancelable) {
        if (mDialog==null){
            mDialog = createLoadingDialog(context, message, cancelable);
            mDialog.show();
        }else if (!isShowing()){
            mDialog.show();
        }

    }

    public void show(Context context, int message, boolean cancelable) {
        if (mDialog==null){
            mDialog = createLoadingDialog(context, context.getResources().getString(message), cancelable);
            mDialog.show();
        }else if (!isShowing()){
            mDialog.show();
        }
    }

    public Dialog createLoadingDialog(Context context, String msg, boolean cancelable) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_my, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView

        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(cancelable);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }

    public void dismissDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog=null;
            }
        } catch (Exception e) {
        }
    }

    public boolean isShowing() {
        if (mDialog == null)
            return false;
        return mDialog.isShowing();
    }
}
