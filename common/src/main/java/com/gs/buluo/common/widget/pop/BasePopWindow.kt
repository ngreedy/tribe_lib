package com.gs.buluo.common.widget.pop

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.widget.PopupWindow
import android.view.WindowManager
import android.view.Gravity
import android.view.Window


/**
 *  author : 王凡
 *  date : 2019/7/11
 *  description :
 */

open class BasePopWindow(mContext: Context) : PopupWindow(mContext), PopupWindow.OnDismissListener {


    private lateinit var window: Window

    init {
        setOnDismissListener(this)
    }

    override fun showAsDropDown(anchor: View) {
        if (Build.VERSION.SDK_INT == 24) {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            val h = anchor.resources.displayMetrics.heightPixels - rect.bottom
            height = h
        }
        super.showAsDropDown(anchor)
    }

    override fun onDismiss() {
        restWindowBackGround()
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) {
        if (Build.VERSION.SDK_INT == 24) {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            val h = anchor.resources.displayMetrics.heightPixels - rect.bottom
            height = h
        }
        super.showAsDropDown(anchor, xoff, yoff)
    }

    fun showAsDropDown(anchor: View, withEffect: Boolean) {
        showAsDropDown(anchor)
        if (withEffect) {
            initWindow(anchor)
            addDartEffect()
        }
    }

    fun showAsDropDown(anchor: View, withEffect: Boolean, xoff: Int, yoff: Int) {
        showAsDropDown(anchor, xoff, yoff)
        if (withEffect) {
            initWindow(anchor)
            addDartEffect()
        }
    }

    /**
     * 添加变暗的效果
     */
    private fun initWindow(anchor: View) {
        var context = anchor.context
        if (context is Activity) {
            window = context.window
        }
    }

    private fun addDartEffect() {
        if (this::window.isInitialized) {
            val lp = window.getAttributes()
            lp.alpha = 0.3f
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.setAttributes(lp)
        }
    }

    private fun restWindowBackGround() {
        if (this::window.isInitialized) {
            val lp = window.getAttributes()
            lp.alpha = 1.0f
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.setAttributes(lp)
        }
    }

}
