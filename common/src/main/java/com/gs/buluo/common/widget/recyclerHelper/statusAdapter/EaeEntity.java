package com.gs.buluo.common.widget.recyclerHelper.statusAdapter;

import android.graphics.drawable.Drawable;

/**
 * Created by Solang on 2018/8/15.
 */

public class EaeEntity {
    public String msg;
    public Drawable drawable;
    public boolean isActVisible;
    public int bacId;
    public int[] marginArgs;

    public EaeEntity(String msg, Drawable drawable, boolean isActVisible, int bacId, int[] margin) {
        this.msg = msg;
        this.drawable = drawable;
        this.isActVisible = isActVisible;
        this.bacId = bacId;
        this.marginArgs = margin;
    }
}
