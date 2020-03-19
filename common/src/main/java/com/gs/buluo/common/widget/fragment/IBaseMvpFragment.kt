package com.gs.buluo.common.widget.fragment

import android.os.Bundle
import android.view.View
import com.gs.buluo.common.widget.presenter.IPresenter
import com.bz.baselibiary.view.IView


abstract class IBaseMvpFragment<V : IView, T : IPresenter<V>> : IBaseFragment() {

    protected var mPresenter: T? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    abstract fun createPresenter(): T?

    override fun onDestroyView() {
        mPresenter?.detachView()
        mPresenter = null
        super.onDestroyView()
    }

}