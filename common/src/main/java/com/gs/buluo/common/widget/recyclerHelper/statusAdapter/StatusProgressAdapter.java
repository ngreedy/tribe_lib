package com.gs.buluo.common.widget.recyclerHelper.statusAdapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gs.buluo.common.R;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class StatusProgressAdapter extends BaseQuickAdapter<EaeEntity, BaseHolder> {


    public StatusProgressAdapter(int layoutResId, @Nullable List<EaeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, EaeEntity item) {
        if (!TextUtils.isEmpty(item.msg)) {
            helper.setText(R.id.progressTextView, item.msg);
        }
        ProgressBar progressBar = helper.getView(R.id.progress_wheel);

        if (item.marginArgs!=null && progressBar != null){
            ((LinearLayout.LayoutParams) progressBar.getLayoutParams()).setMargins(item.marginArgs[0],
                    item.marginArgs[1], item.marginArgs[2], item.marginArgs[3]);
        }
    }

}
