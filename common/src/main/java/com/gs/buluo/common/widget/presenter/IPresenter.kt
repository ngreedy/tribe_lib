package com.gs.buluo.common.widget.presenter

import com.bz.baselibiary.view.IView

interface IPresenter<V : IView> {
    /**
     * 绑定View
     */
    fun attachView(view: V)

    /**
     * 分离View
     */
    fun detachView()

    /**
     * 判断view是否已经销毁
     * @return true 未销毁
     */
    fun isViewAttach(): Boolean

    fun getView(): V?
}
