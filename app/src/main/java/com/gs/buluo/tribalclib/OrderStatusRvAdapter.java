package com.gs.buluo.tribalclib;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gs.buluo.common.utils.TribeDateUtils;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;


import java.util.Date;
import java.util.List;


/**
 * Created by solang on 2018/2/5.
 */

public class OrderStatusRvAdapter extends BaseQuickAdapter<Integer, BaseHolder> {
    private Activity activity;

    public OrderStatusRvAdapter(Activity activity, int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper, Integer item) {

    }


}
