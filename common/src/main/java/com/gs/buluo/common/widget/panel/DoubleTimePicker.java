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
 * Created by hjn on 2017/1/20.
 */
public class DoubleTimePicker extends Dialog {
    Context context;

    public DoubleTimePicker(Context context) {
        super(context, R.style.my_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    public static class Builder<T> implements View.OnClickListener, OnWheelChangedListener {
        private final Context mContext;
        private final OnSelectedFinished mOnSelectedFinished;
        private String title = "请选择时间";
        private T firstResult;
        private T secondResult;
        private SimpleChoosePanel mSimpleChoosePanel;
        private ArrayList<T> firstData;
        private ArrayList<T> secondData;
        private WheelView wheelView1;
        private WheelView wheelView2;
        private int secondCurrent = 0;
        private int firstCurrent = 0;

        public Builder(Context context, OnSelectedFinished onSelectedFinished) {
            mContext = context;
            mOnSelectedFinished = onSelectedFinished;
        }

        public DoubleTimePicker.Builder<T> setTitle(String title) {
            this.title = title;
            return this;
        }


        public DoubleTimePicker.Builder<T> setFirstData(ArrayList<T> data) {
            this.firstData = data;
            firstResult = data.get(0);
            return this;
        }

        public DoubleTimePicker.Builder<T> setSecondData(ArrayList<T> data) {
            this.secondData = data;
            secondResult = data.get(0);
            return this;
        }
        public DoubleTimePicker.Builder<T> setFirstCurrent(int firstCurrent) {
            this.firstCurrent = firstCurrent;
            return this;
        }

        public DoubleTimePicker.Builder<T> setSecondCurrent(int secondCurrent) {
           this.secondCurrent = secondCurrent;
            return this;
        }

        public SimpleChoosePanel build() {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View rootView = inflater.inflate(R.layout.info_time_picker_board, null);
            ((TextView) rootView.findViewById(R.id.double_choose_title)).setText(title);
            wheelView1 = (WheelView) rootView.findViewById(R.id.id_hour);
            wheelView2 = (WheelView) rootView.findViewById(R.id.id_mini);
            wheelView1.addChangingListener(this);
            wheelView2.addChangingListener(this);

            rootView.findViewById(R.id.btn_confirm).setOnClickListener(this);
            rootView.findViewById(R.id.btn_cancel).setOnClickListener(this);
            List<T> list = new ArrayList<>();
            List<T> list2 = new ArrayList<>();
            if (firstData != null) {
                list.addAll(firstData);
            }
            if (secondData != null) {
                list2.addAll(secondData);
            }
            ArrayWheelAdapter<T> viewAdapter1 = new ArrayWheelAdapter<>(mContext, (T[]) list.toArray());
            ArrayWheelAdapter<T> viewAdapter2 = new ArrayWheelAdapter<>(mContext, (T[]) list2.toArray());
            wheelView1.setViewAdapter(viewAdapter1);
            wheelView2.setViewAdapter(viewAdapter2);
            wheelView1.setCurrentItem(firstCurrent);
            wheelView2.setCurrentItem(secondCurrent);
            mSimpleChoosePanel = new SimpleChoosePanel(mContext);
            mSimpleChoosePanel.setContentView(rootView);
            mSimpleChoosePanel.show();
            return mSimpleChoosePanel;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_confirm) {
                mOnSelectedFinished.onSelected(firstResult.toString() + "-" + secondResult.toString());
            }
            mSimpleChoosePanel.dismiss();
        }

        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (wheel.getId() == R.id.id_hour) {
                firstResult = firstData.get(newValue);
            } else {
                secondResult = secondData.get(newValue);
            }
            if (firstResult == null) {
                firstResult = firstData.get(0);
            }
        }

    }

    public interface OnSelectedFinished {
        void onSelected(String result);
    }
}
