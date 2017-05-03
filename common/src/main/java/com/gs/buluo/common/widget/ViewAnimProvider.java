package com.gs.buluo.common.widget;

import android.view.animation.Animation;

/**
 * Created by hjn on 2017/5/3.
 * 实现该接口，定制动画
 */

public interface ViewAnimProvider {
    Animation showAnimation();

    Animation hideAnimation();
}
