package com.gs.buluo.common.widget.panel;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gs.buluo.common.R;
import com.gs.buluo.common.widget.wheel.OnWheelChangedListener;
import com.gs.buluo.common.widget.wheel.WheelView;
import com.gs.buluo.common.widget.wheel.adapters.ArrayWheelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fs on 2016/12/20.
 */

public class SimpleChoosePanel extends Dialog {
    public SimpleChoosePanel(Context context) {
        super(context, R.style.my_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width= ViewGroup.LayoutParams.MATCH_PARENT;
        params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    public interface OnSelectedFinished<T>{
        void onSelected(T string);
    }

    public static class Builder<T> implements View.OnClickListener, OnWheelChangedListener {
        private final Context mContext;
        private final OnSelectedFinished mOnSelectedFinished;
        private String title="请选择";
        private T result;
        private SimpleChoosePanel mSimpleChoosePanel;
        private ArrayList<T> data;

        public Builder(Context context, OnSelectedFinished onSelectedFinished) {
            mContext = context;
            mOnSelectedFinished = onSelectedFinished;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        public Builder setData(ArrayList<T> data) {
            this.data = data;
            result = data.get(0);
            return this;
        }


        public SimpleChoosePanel build(){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.simple_choose_board, null);
            ((TextView) view.findViewById(R.id.simple_choose_title)).setText(title);
            WheelView wheelView = (WheelView) view.findViewById(R.id.simple_choose_wheel);
            wheelView.addChangingListener(this);
            view.findViewById(R.id.simple_choose_confirm).setOnClickListener(this);
            view.findViewById(R.id.simple_choose_cancel).setOnClickListener(this);
            List<T> list =new ArrayList<>();
            if (data!=null){
                list.addAll(data);
            }
            ArrayWheelAdapter<T> viewAdapter = new ArrayWheelAdapter<>(mContext, (T[]) list.toArray());
            wheelView.setViewAdapter(viewAdapter);

            mSimpleChoosePanel = new SimpleChoosePanel(mContext);
            mSimpleChoosePanel.setContentView(view);

            return mSimpleChoosePanel;
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.simple_choose_confirm){
                if (result==null){
                    mOnSelectedFinished.onSelected(null);
                }else {
                    mOnSelectedFinished.onSelected(result);
                }
            }
            mSimpleChoosePanel.dismiss();
        }


        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            result = data.get(newValue);
            if (result==null){
                result =data.get(0);
            }
        }
    }
}
