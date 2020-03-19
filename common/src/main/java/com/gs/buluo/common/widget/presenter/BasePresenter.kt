package com.gs.buluo.common.widget.presenter


import com.bz.baselibiary.view.IView
import java.lang.ref.WeakReference


abstract class BasePresenter<V : IView> : IPresenter<V> {
    private var weakReference: WeakReference<V>? = null

    override fun attachView(view: V) {
        weakReference = WeakReference(view)
    }

    override fun detachView() {
        if (weakReference != null) {
            weakReference!!.clear()
            weakReference = null
        }
    }

    override fun isViewAttach(): Boolean {
        return weakReference?.get() != null
    }

    override fun getView(): V? {
        return weakReference?.get()
    }

    fun showLoadingDialog() {
        if (isViewAttach()) {
            getView()?.showLoadingDialog()
        }
    }

    fun hideLoadingDialog() {
        if (isViewAttach()) {
            getView()?.hideLoadingDialog()
        }
    }
}
