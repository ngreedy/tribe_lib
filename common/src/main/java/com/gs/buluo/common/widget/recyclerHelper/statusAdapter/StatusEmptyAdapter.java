package com.gs.buluo.common.widget.recyclerHelper.statusAdapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gs.buluo.common.R;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class StatusEmptyAdapter extends BaseQuickAdapter<EaeEntity, BaseHolder> {


    public StatusEmptyAdapter(int layoutResId, @Nullable List<EaeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, EaeEntity item) {
        if (TextUtils.isEmpty(item.msg)) {
            helper.setText(R.id.emptyTextView, item.msg);
        }
        helper.setVisible(R.id.empty_click_view, item.isActVisible);
        ImageView imageView = helper.getView(R.id.emptyImageView);
        if (item.drawable != null) {
            imageView.setImageDrawable(item.drawable);
        }
        if (item.bacId != 0) {
            helper.setBackgroundRes(R.id.empty_click_view, item.bacId);
        }
        if (item.marginArgs!=null){
            ((LinearLayout.LayoutParams) imageView.getLayoutParams()).setMargins(item.marginArgs[0],
                    item.marginArgs[1], item.marginArgs[2], item.marginArgs[3]);
        }
    }

}
