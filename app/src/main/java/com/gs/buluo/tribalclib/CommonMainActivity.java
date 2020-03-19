package com.gs.buluo.tribalclib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gs.buluo.common.utils.DensityUtils;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.LoadingDialog;
import com.gs.buluo.common.widget.StatusLayout;
import com.gs.buluo.common.widget.recyclerHelper.OnRefreshListener;
import com.gs.buluo.common.widget.recyclerHelper.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hjn on 2017/3/29.
 */

public class CommonMainActivity extends Activity {
    RefreshRecyclerView recyclerView;
    OrderStatusRvAdapter adapter;
    List<Integer> data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_main);
        for (int i = 0; i < 30; i++) {
            data.add(1);
        }
        recyclerView = findViewById(R.id.rv_orders);
        recyclerView.getRecyclerView().setEmptyAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.ToastMessage(getApplication(),"hehe");
            }
        });
        recyclerView.getRecyclerView().setEmptyActionBackground(R.mipmap.ic_launcher);
        adapter = new OrderStatusRvAdapter(this, R.layout.item_order_status, data);

        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshAction(new OnRefreshListener() {
            @Override
            public void onAction() {
                recyclerView.getRecyclerView().setEmptyActViewText("去哪儿");
                recyclerView.showEmptyView("lala");
            }
        });
    }
}
