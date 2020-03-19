package com.gs.buluo.common.widget.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gs.buluo.common.BaseApplication;
import com.gs.buluo.common.R;

import java.util.Observable;
import java.util.Observer;


/**
 * Created by hjn on 2017/4/11.
 */

public class GlobalDialogService extends Service implements Observer {
    private Dialog mDialog;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        String message=(String) arg;
        if(message!=null){
            if (mDialog == null) {
                mDialog = new Dialog(BaseApplication.getInstance().getApplicationContext(),R.style.sheet_dialog);
                View view =View.inflate(BaseApplication.getInstance().getApplicationContext(),R.layout.simple_dialog,null);
                mDialog.setContentView(view,new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
                TextView textView = (TextView) view.findViewById(R.id.message);
                textView.setText(message);
                mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
//                mDialog = ProgressDialog.show(TribeApplication.getInstance().getApplicationContext(), null, message);
            }
            if (mDialog != null && !mDialog.isShowing()) {
                // 加入系统服务
                mDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_TOAST));
                mDialog.show();
            }
        }else {
            if(mDialog!=null){
                mDialog.cancel();
                mDialog=null;
            }
        }
    }


}
