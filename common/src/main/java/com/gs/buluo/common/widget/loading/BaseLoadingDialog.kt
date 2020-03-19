package com.gs.buluo.common.widget.loading

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gs.buluo.common.widget.dialog.BaseDialogFragment

abstract class BaseLoadingDialog : BaseDialogFragment() {
    lateinit var loadingView: BaseLoadingView

    override fun getLayoutId(): Int {
        return -1
    }

    override fun initWindow() {
        configWindow(
            Gravity.CENTER,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun createLayoutView(inflater: LayoutInflater, container: ViewGroup?): View? {
        loadingView = genLoadingView()
        return loadingView.createContentView(context, inflater, container)
    }

    abstract fun genLoadingView(): BaseLoadingView
    /***
     *
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingView.startAnimation()
        setCanceledOnTouchOutside(false)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        if (::loadingView.isInitialized)
            loadingView.endAnimation()
    }

    override fun dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss()
        if (::loadingView.isInitialized)
            loadingView.endAnimation()
    }

    override fun initViews() {
    }


    override fun initVariables(bundle: Bundle?) {
    }


    override fun initData() {
    }

}