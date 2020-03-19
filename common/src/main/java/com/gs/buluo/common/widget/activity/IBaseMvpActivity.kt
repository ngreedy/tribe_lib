package com.gs.buluo.common.widget.activity

import android.os.Bundle
import android.view.animation.RotateAnimation
import com.gs.buluo.common.widget.presenter.IPresenter
import com.bz.baselibiary.view.IView

abstract class IBaseMvpActivity<V : IView, T : IPresenter<V>> : IBaseActivity() {
    var mPresenter: T? = null

    override fun initActivity(savedInstanceState: Bundle?) {
        initVariables()
        var layoutId = getLayoutId()
        if (layoutId != -1) {
            setContentView(getLayoutId())
        }
        initViews(savedInstanceState)
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
        initData()
    }

    abstract fun createPresenter(): T?

    override fun onDestroy() {
        mPresenter?.detachView()
        mPresenter = null
        super.onDestroy()
    }
}